package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.ContactDao;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.dto.ContactDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.assembler.ContactAssembler;
import com.ecc.hibernate_xml.assembler.PersonAssembler;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class ContactService extends AbstractService<Contact, ContactDTO> {
	private static final Integer MAX_CHARACTERS = 50;
	private static final Integer LANDLINE_DIGITS = 7;
	private static final Integer MOBILE_NUMBER_DIGITS = 11;

	private final ContactDao contactDao;
	private final ModelValidator validator;
	private final PersonAssembler personAssembler;

	public ContactService() {
		super(new ContactDao(), new ContactAssembler());
		contactDao = (ContactDao) dao;
		validator = ModelValidator.create();
		personAssembler = new PersonAssembler();
	}

	public String validateContact(String data, String contactType) throws ValidationException {
		switch(contactType) {
			case "Landline":
				validateNumericalContact(data, LANDLINE_DIGITS, "Landline"); 
				break;
			case "Mobile Number":
				validateNumericalContact(data, MOBILE_NUMBER_DIGITS, "Mobile number"); 
				break;
			case "Email":
				validateEmail(data);
				break;
			default: 
				throw new RuntimeException("No validation rule defined for " + contactType + "!");
		}
		return data;
	}

	private void validateNumericalContact(String data, Integer matchingDigits, String contactType) throws ValidationException {		
		validator.validate("NotEmpty", data, contactType);
		validator.validate("Digits", data, contactType);
		validator.validate("EqualLength", data, matchingDigits, contactType);
	}

	private void validateEmail(String email) throws ValidationException {
		validator.validate("NotEmpty", email, "Email");
		validator.validate("MaxLength", email, MAX_CHARACTERS, "Email");
		validator.validate("ValidEmail", email);
	}

	public List<ContactDTO> list(PersonDTO personDTO) {
		Person person = personAssembler.createModel(personDTO);
		return assembler.createDTO(contactDao.list(person));
	}

	public ContactDTO get(Integer id, PersonDTO personDTO) throws DaoException {
		Person person = personAssembler.createModel(personDTO);
		return assembler.createDTO(contactDao.get(id, person));
	}

	public void delete(Integer id, PersonDTO personDTO) throws DaoException {
		Person person = personAssembler.createModel(personDTO);
		contactDao.delete(contactDao.get(id, person));
	}
}