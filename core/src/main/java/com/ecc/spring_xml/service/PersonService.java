package com.ecc.spring_xml.service;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.BindException;
import org.springframework.dao.DataRetrievalFailureException;
import org.apache.commons.lang3.StringUtils;

import com.ecc.spring_xml.dto.PersonDTO;
import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.dto.ContactDTO;
import com.ecc.spring_xml.model.Person;
import com.ecc.spring_xml.assembler.PersonAssembler;
import com.ecc.spring_xml.assembler.RoleAssembler;
import com.ecc.spring_xml.dao.PersonDao;
import com.ecc.spring_xml.dao.RoleDao;
import com.ecc.spring_xml.util.app.AssemblerUtils;
import com.ecc.spring_xml.util.ValidationUtils;
import com.ecc.spring_xml.util.ValidationException;

public class PersonService extends AbstractService<Person, PersonDTO> implements Validator {
	private static final Integer DEFAULT_MAX_CHARACTERS = 20;
	private static final Integer LONG_MAX_CHARACTERS = 50;
	private static final Integer LANDLINE_DIGITS = 7;
	private static final Integer MOBILE_NUMBER_DIGITS = 11;

	private final PersonDao personDao;
	private final PersonAssembler personAssembler;
	private final RoleDao roleDao;
	private final RoleAssembler roleAssembler;

	public PersonService(PersonDao personDao, PersonAssembler personAssembler, RoleDao roleDao, RoleAssembler roleAssembler) {
		super(personDao, personAssembler);
		this.personDao = personDao;
		this.personAssembler = personAssembler;
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
	}

	@Override
	public boolean supports(Class clazz) {
        return clazz.isAssignableFrom(PersonDTO.class);
    }

    public void validate(PersonDTO person, String objectName) {
    	if (person == null) {
    		throw new ValidationException("validation.message.notEmpty", "localize:person.form.label");
    	}

    	Errors errors = new BindException(person, objectName);
    	validate(person, errors);
    	if (errors.hasErrors()) {
    		throw new ValidationException(errors.getAllErrors());
    	}
    }

    @Override
    public void validate(Object command, Errors errors) {
    	PersonDTO person = (PersonDTO) command;

		validateName(person.getName().getTitle(), "name.title", errors, "localize:person.form.label.name.title");
		validateName(person.getName().getLastName(), "name.lastName", errors, "localize:person.form.label.name.lastName");
		validateName(person.getName().getFirstName(), "name.firstName", errors, "localize:person.form.label.name.firstName");
		validateName(person.getName().getMiddleName(), "name.middleName", errors, "localize:person.form.label.name.middleName");
		validateName(person.getName().getSuffix(), "name.suffix", errors, "localize:person.form.label.name.suffix");

		validateAddress(person.getAddress().getStreetNumber(), "address.streetNumber", errors, "localize:person.form.label.address.streetNumber");
		validateAddress(person.getAddress().getBarangay(), "address.barangay", errors, "localize:person.form.label.address.barangay");
		validateAddress(person.getAddress().getMunicipality(), "address.municipality", errors, "localize:person.form.label.address.municipality");
		validateAddress(person.getAddress().getZipCode(), "address.zipCode", errors, "localize:person.form.label.address.zipCode");

		ValidationUtils.testNotEmpty(person.getBirthday(), "birthday", errors, "localize:person.form.label.otherInformation.birthday");

		validateGWA(person.getGWA(), "GWA", errors, "localize:person.form.label.otherInformation.GWA");

		if (!person.getCurrentlyEmployed() && person.getDateHired() != null) {
			errors.rejectValue("dateHired", "localize:person.validation.message.unemployed");
		}
		else if (person.getCurrentlyEmployed() && person.getDateHired() == null) {
			errors.rejectValue("dateHired", "localize:person.validation.message.employed");
		}

		validateContacts(person.getContacts(), errors);
		validateRoles(person.getRoles(), errors);
    }

	public List<PersonDTO> list(String lastName, Integer roleId, Date birthday, String orderBy, String order) {
		return AssemblerUtils.asList(personDao.list(lastName, roleId, birthday, orderBy, order), personAssembler::createDTO);
	}

	private void validateName(String data, String field, Errors errors, String argument) {
		if (!StringUtils.containsIgnoreCase(field, "title") && !StringUtils.containsIgnoreCase(field, "suffix")) {
			ValidationUtils.testNotEmpty(data, field, errors, argument);
		}
		ValidationUtils.testMaxLength(data, field, errors, DEFAULT_MAX_CHARACTERS, argument);
	}

	private void validateAddress(Object data, String field, Errors errors, String argument) {
		ValidationUtils.testNotEmpty(data, field, errors, argument);

		if (data instanceof String && StringUtils.containsIgnoreCase(field, "street")) {
			ValidationUtils.testMaxLength((String) data, field, errors, DEFAULT_MAX_CHARACTERS, argument);
		}
		else if (data instanceof String && (StringUtils.containsIgnoreCase(field, "municipality") || StringUtils.containsIgnoreCase(field, "barangay"))) {
			ValidationUtils.testMaxLength((String) data, field, errors, LONG_MAX_CHARACTERS, argument);
		}
	}

	private void validateGWA(BigDecimal GWA, String field, Errors errors, String argument) {
		ValidationUtils.testNotEmpty(GWA, field, errors, argument);
		ValidationUtils.testMinimumValue(GWA, field, errors, 1, argument);
		ValidationUtils.testMaximumValue(GWA, field, errors, 5, argument);
	}

	private void validateRoles(List<RoleDTO> roles, Errors errors) {
		for (RoleDTO role: roles) {
			try {
				roleDao.get(role.getId());			
			}
			catch (DataRetrievalFailureException cause) {
				errors.reject("localize:person.validation.roles.notFound", role.getId().toString());
			}
		}
	}

	private void validateContacts(List<ContactDTO> contacts, Errors errors) {
		for (int i = 0; i < contacts.size(); i++) {
			ContactDTO contact = contacts.get(i);
			ValidationUtils.testNotEmpty(contact.getContactType(), null, errors, "localize:person.contactType");
			switch(contact.getContactType()) {
				case "Landline":
					validateNumericalContact(contact.getData(), errors, LANDLINE_DIGITS, "localize:person.contactType.landline"); 
					break;
				case "Mobile":
					validateNumericalContact(contact.getData(), errors, MOBILE_NUMBER_DIGITS, "localize:person.contactType.mobile"); 
					break;
				case "Email":
					validateEmail(contact.getData(), errors);
					break;
				default: 
					errors.reject("localize:person.validation.contactType.invalid", contact.getContactType());
			}
		}
	}

	private void validateNumericalContact(String data, Errors errors, Integer matchingDigits, String argument) {
		ValidationUtils.testNotEmpty(data, null, errors, argument);
		ValidationUtils.testDigits(data, null, errors, argument);
		ValidationUtils.testEqualLength(data, null, errors, matchingDigits, argument);
	}

	private void validateEmail(String email, Errors errors) {
		ValidationUtils.testNotEmpty(email, null, errors, "localize:person.contactType.email");
		ValidationUtils.testValidEmail(email, null, errors);
		ValidationUtils.testMaxLength(email, null, errors, LONG_MAX_CHARACTERS, "localize:person.contactType.email");
	}
}