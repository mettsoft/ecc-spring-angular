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

	public static String validateLandline(String landline) throws ValidationException {
		ModelValidator
			.create(landline)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Landline"))
			.digits(String.format(NUMERICAL_DIGITS_ERROR_MESSAGE, "Landline"))
			.equalLength(LANDLINE_DIGITS, String.format(EQUALS_ERROR_MESSAGE, "Landline", LANDLINE_DIGITS))
			.validate();

		return landline;
	}

	public static String validateEmail(String email) throws ValidationException {
		ModelValidator
			.create(email)
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Email"))
			.validEmail(EMAIL_IS_INVALID)
			.validate();

		return email;
	}

	public static String validateMobileNumber(String mobileNumber) throws ValidationException {
		ModelValidator
			.create(mobileNumber)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Mobile number"))
			.digits(String.format(NUMERICAL_DIGITS_ERROR_MESSAGE, "Mobile number"))
			.equalLength(MOBILE_NUMBER_DIGITS, String.format(EQUALS_ERROR_MESSAGE, "Mobile number", 
				MOBILE_NUMBER_DIGITS))
			.validate();

		return mobileNumber;
	}

	public List<Contact> list(Person person) {
		return contactDao.list(person);
	}

	public void create(Contact contact, Person person) throws DaoException {
		contact.setPerson(person);
		contactDao.create(contact);
	}
}