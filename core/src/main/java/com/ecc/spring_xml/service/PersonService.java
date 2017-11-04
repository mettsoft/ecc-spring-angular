package com.ecc.spring_xml.service;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

import com.ecc.spring_xml.dto.PersonDTO;
import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.dto.ContactDTO;
import com.ecc.spring_xml.model.Person;
import com.ecc.spring_xml.assembler.PersonAssembler;
import com.ecc.spring_xml.assembler.RoleAssembler;
import com.ecc.spring_xml.dao.PersonDao;
import com.ecc.spring_xml.dao.RoleDao;
import com.ecc.spring_xml.util.app.AssemblerUtils;
import com.ecc.spring_xml.util.validator.ValidationException;
import com.ecc.spring_xml.util.validator.ModelValidator;

public class PersonService extends AbstractService<Person, PersonDTO> {
	private static final Integer DEFAULT_MAX_CHARACTERS = 20;
	private static final Integer LONG_MAX_CHARACTERS = 50;
	private static final Integer LANDLINE_DIGITS = 7;
	private static final Integer MOBILE_NUMBER_DIGITS = 11;

	private final PersonDao personDao;
	private final PersonAssembler personAssembler;
	private final RoleDao roleDao;
	private final RoleAssembler roleAssembler;
	private final ModelValidator validator;

	public PersonService(PersonDao personDao, PersonAssembler personAssembler, RoleDao roleDao, RoleAssembler roleAssembler, ModelValidator validator) {
		super(personDao, personAssembler);
		this.personDao = personDao;
		this.personAssembler = personAssembler;
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
		this.validator = validator;
	}

	public void validate(PersonDTO person) {
		validator.validate("NotEmpty", person, "Person");
		validator.validate("NotEmpty", person.getName(), "Name");
		validateName(person.getName().getTitle(), "Title");
		validateName(person.getName().getLastName(), "Last name");
		validateName(person.getName().getFirstName(), "First name");
		validateName(person.getName().getMiddleName(), "Middle name");
		validateName(person.getName().getSuffix(), "Suffix");
		validator.validate("NotEmpty", person.getAddress(), "Address");
		validateAddress(person.getAddress().getStreetNumber(), "Street number");
		validateAddress(person.getAddress().getBarangay(), "Barangay");
		validateAddress(person.getAddress().getMunicipality(), "Municipality");
		validateAddress(person.getAddress().getZipCode(), "Zip code");
		validateBirthday(person.getBirthday());
		validateGWA(person.getGWA());
		validateDateHired(person.getCurrentlyEmployed(), person.getDateHired());
		validateContacts(person.getContacts());
		validateRoles(person.getRoles());
	}

	public List<PersonDTO> list(String lastName, Integer roleId, Date birthday, String orderBy, String order) {
		return AssemblerUtils.asList(personDao.list(lastName, roleId, birthday, orderBy, order), personAssembler::createDTO);
	}

	private void validateName(String data, String component) {
		if (!component.equals("Title") && !component.equals("Suffix")) {
			validator.validate("NotEmpty", data, component);		
		}
		validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);
	}

	private <T> void validateAddress(T data, String component) {
		validator.validate("NotEmpty", data, component);

		if (component.equals("Street number")) {
			validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);		
		}
		else if (component.equals("Municipality") || component.equals("Barangay")) {
			validator.validate("MaxLength", data, LONG_MAX_CHARACTERS, component);			
		}
	}

	private void validateBirthday(Date birthday) {
		validator.validate("NotEmpty", birthday, "Birthday");
	}

	private void validateGWA(BigDecimal GWA) {
		validator.validate("NotEmpty", GWA, "GWA");
		validator.validate("Minimum", GWA, 1, "GWA");
		validator.validate("Maximum", GWA, 5, "GWA");
	}

	private void validateDateHired(Boolean currentlyEmployed, Date dateHired) {
		if (!currentlyEmployed && dateHired != null) {
			throw new ValidationException("Date hired cannot be assigned if person is unemployed.");
		}
		else if (currentlyEmployed && dateHired == null) {
			throw new ValidationException("Date hired cannot be empty if person is employed.");
		}
	}

	private void validateContacts(List<ContactDTO> contacts) {
		for (ContactDTO contact: contacts) {
			validator.validate("NotEmpty", contact.getContactType(), "Contact type");
			switch(contact.getContactType()) {
				case "Landline":
					validateNumericalContact(contact.getData(), LANDLINE_DIGITS, "Landline"); 
					break;
				case "Mobile":
					validateNumericalContact(contact.getData(), MOBILE_NUMBER_DIGITS, "Mobile number"); 
					break;
				case "Email":
					validateEmail(contact.getData());
					break;
				default: 
					throw new RuntimeException("Contact type \"" + contact.getContactType() + "\" is not recognized!");
			}			
		}
	}

	private void validateRoles(List<RoleDTO> roles) {
		roleDao.throwIfNotExists(AssemblerUtils.asList(roles, roleAssembler::createModel));
	}

	private void validateNumericalContact(String data, Integer matchingDigits, String contactType) {		
		validator.validate("NotEmpty", data, contactType);
		validator.validate("Digits", data, contactType);
		validator.validate("EqualLength", data, matchingDigits, contactType);
	}

	private void validateEmail(String email) {
		validator.validate("NotEmpty", email, "Email");
		validator.validate("MaxLength", email, LONG_MAX_CHARACTERS, "Email");
		validator.validate("ValidEmail", email);
	}
}