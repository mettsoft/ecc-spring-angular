package com.ecc.servlets.service;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

import com.ecc.servlets.dto.PersonDTO;
import com.ecc.servlets.dto.ContactDTO;
import com.ecc.servlets.assembler.PersonAssembler;
import com.ecc.servlets.dao.PersonDao;
import com.ecc.servlets.model.Person;
import com.ecc.servlets.util.app.AssemblerUtils;
import com.ecc.servlets.util.validator.ValidationException;
import com.ecc.servlets.util.validator.ModelValidator;

public class PersonService extends AbstractService<Person, PersonDTO> {
	private static final Integer DEFAULT_MAX_CHARACTERS = 20;
	private static final Integer LONG_MAX_CHARACTERS = 50;
	private static final Integer MAX_EMAIL_CHARACTERS = 50;
	private static final Integer LANDLINE_DIGITS = 7;
	private static final Integer MOBILE_NUMBER_DIGITS = 11;

	private final PersonDao personDao;
	private final ModelValidator validator;

	public PersonService() {
		super(new PersonDao(), new PersonAssembler());
		personDao = (PersonDao) dao;
		validator = ModelValidator.create();
	}

	public void validate(PersonDTO person) throws ValidationException {
		validateName(person.getName().getTitle(), "Title");
		validateName(person.getName().getLastName(), "Last name");
		validateName(person.getName().getFirstName(), "First name");
		validateName(person.getName().getMiddleName(), "Middle name");
		validateName(person.getName().getSuffix(), "Suffix");
		validateAddress(person.getAddress().getStreetNumber(), "Street number");
		validateAddress(person.getAddress().getBarangay(), "Barangay");
		validateAddress(person.getAddress().getMunicipality(), "Municipality");
		validateAddress(person.getAddress().getZipCode(), "Zip code");
		validateBirthday(person.getBirthday());
		validateGWA(person.getGWA());
		validateDateHired(person.getCurrentlyEmployed(), person.getDateHired());
		validateContacts(person.getContacts());
	}

	public List<PersonDTO> list(String lastName, Integer roleId, Date birthday, String orderBy, String order) {
		return AssemblerUtils.asList(personDao.list(lastName, roleId, birthday, orderBy, order), assembler::createDTO);
	}

	private void validateName(String data, String component) throws ValidationException {
		if (!component.equals("Title") && !component.equals("Suffix")) {
			validator.validate("NotEmpty", data, component);		
		}
		validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);
	}

	private <T> void validateAddress(T data, String component) throws ValidationException {
		validator.validate("NotEmpty", data, component);

		if (component.equals("Street number")) {
			validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);		
		}
		else if (component.equals("Municipality")) {
			validator.validate("MaxLength", data, LONG_MAX_CHARACTERS, component);			
		}
		else if (component.equals("Barangay")) {
			validator.validate("MaxLength", data, LONG_MAX_CHARACTERS, component);			
		}
	}

	private void validateBirthday(Date birthday) throws ValidationException {
		validator.validate("NotEmpty", birthday, "Birthday");
	}

	private void validateGWA(BigDecimal GWA) throws ValidationException {
		validator.validate("NotEmpty", GWA, "GWA");
		validator.validate("Minimum", GWA, 1, "GWA");
		validator.validate("Maximum", GWA, 5, "GWA");
	}

	private void validateDateHired(Boolean currentlyEmployed, Date dateHired) throws ValidationException {
		if (!currentlyEmployed && dateHired != null) {
			throw new ValidationException("Date hired cannot be assigned if person is unemployed.");
		}
		else if (currentlyEmployed && dateHired == null) {
			throw new ValidationException("Date hired cannot be empty if person is employed.");
		}
	}

	private void validateContacts(List<ContactDTO> contacts) throws ValidationException {
		for (ContactDTO contact: contacts) {
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
					throw new RuntimeException("No validation rule defined for " + contact.getContactType() + "!");
			}			
		}
	}

	private void validateNumericalContact(String data, Integer matchingDigits, String contactType) throws ValidationException {		
		validator.validate("NotEmpty", data, contactType);
		validator.validate("Digits", data, contactType);
		validator.validate("EqualLength", data, matchingDigits, contactType);
	}

	private void validateEmail(String email) throws ValidationException {
		validator.validate("NotEmpty", email, "Email");
		validator.validate("MaxLength", email, MAX_EMAIL_CHARACTERS, "Email");
		validator.validate("ValidEmail", email);
	}
}