package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.ContactDao;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class ContactService extends AbstractService<Contact> {
	private static final Integer MAX_CHARACTERS = 50;
	private static final Integer LANDLINE_DIGITS = 7;
	private static final Integer MOBILE_NUMBER_DIGITS = 11;

	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed " + MAX_CHARACTERS + " characters.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";
	private static final String NUMERICAL_DIGITS_ERROR_MESSAGE = "%s must only contain numerical digits.";
	private static final String EQUALS_ERROR_MESSAGE = "%s must contain %d digits.";
	private static final String EMAIL_IS_INVALID = "Email is invalid.";

	private ContactDao contactDao;

	public ContactService() {
		super(new ContactDao());
		contactDao = (ContactDao) dao;
	}

	public static String validateContact(String data, String contactType) throws ValidationException {
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

	private static void validateNumericalContact(String data, Integer matchingDigits, String contactType) throws ValidationException {
		ModelValidator
			.create(data)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, contactType))
			.digits(String.format(NUMERICAL_DIGITS_ERROR_MESSAGE, contactType))
			.equalLength(matchingDigits, String.format(EQUALS_ERROR_MESSAGE, contactType, matchingDigits))
			.validate();
	}

	private static void validateEmail(String email) throws ValidationException {
		ModelValidator
			.create(email)
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Email"))
			.validEmail(EMAIL_IS_INVALID)
			.validate();
	}

	public List<Contact> list(Person person) {
		return contactDao.list(person);
	}

	public void create(Contact contact, Person person) throws DaoException {
		contact.setPerson(person);
		contactDao.create(contact);
	}
}