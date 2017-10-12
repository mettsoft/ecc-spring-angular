package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class PersonService extends AbstractService<Person> {
	private static final Integer DEFAULT_MAX_CHARACTERS = 20;
	private static final Integer MAX_MUNICIPALITY_CHARACTERS = 50;

	private static final String DEFAULT_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed " + DEFAULT_MAX_CHARACTERS + " characters.";
	private static final String MUNICIPALITY_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed " + MAX_MUNICIPALITY_CHARACTERS + " characters.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";
	private static final String NOT_NULL_ERROR_MESSAGE_TEMPLATE = "%s cannot be null.";

	private PersonDao personDao;

	public PersonService() {
		super(new PersonDao());
		personDao = (PersonDao) dao;
	}

	public static String validateTitle(String title) throws ValidationException {
		ModelValidator
			.create(title)
			.maxLength(DEFAULT_MAX_CHARACTERS, String.format(DEFAULT_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Title"))
			.validate();

		return title;
	}

	public static String validateLastName(String lastName) throws ValidationException {
		ModelValidator
			.create(lastName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Last name"))
			.maxLength(DEFAULT_MAX_CHARACTERS, String.format(DEFAULT_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Last name"))
			.validate();

		return lastName;
	}

	public static String validateFirstName(String firstName) throws ValidationException {
		ModelValidator
			.create(firstName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "First name"))
			.maxLength(DEFAULT_MAX_CHARACTERS, String.format(DEFAULT_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"First name"))
			.validate();

		return firstName;
	}

	public static String validateMiddleName(String middleName) throws ValidationException {
		ModelValidator
			.create(middleName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Middle name"))
			.maxLength(DEFAULT_MAX_CHARACTERS, String.format(DEFAULT_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Middle name"))
			.validate();

		return middleName;
	}

	public static String validateSuffix(String suffix) throws ValidationException {
		ModelValidator
			.create(suffix)
			.maxLength(DEFAULT_MAX_CHARACTERS, String.format(DEFAULT_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Suffix"))
			.validate();

		return suffix;
	}

	public static String validateStreetNumber(String streetNumber) throws ValidationException {
		ModelValidator
			.create(streetNumber)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Street number"))
			.maxLength(DEFAULT_MAX_CHARACTERS, String.format(DEFAULT_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Street number"))
			.validate();

		return streetNumber;
	}

	public static Integer validateBarangay(Integer barangay) throws ValidationException {
		ModelValidator
			.create(barangay)
			.notNull(String.format(NOT_NULL_ERROR_MESSAGE_TEMPLATE, "Barangay"))
			.validate();

		return barangay;
	}

	public static String validateMunicipality(String municipality) throws ValidationException {
		ModelValidator
			.create(municipality)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Municipality"))
			.maxLength(MAX_MUNICIPALITY_CHARACTERS, String.format(MUNICIPALITY_MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Municipality"))
			.validate();

		return municipality;
	}

	public static Integer validateZipCode(Integer zipCode) throws ValidationException {
		ModelValidator
			.create(zipCode)
			.notNull(String.format(NOT_NULL_ERROR_MESSAGE_TEMPLATE, "Zip code"))
			.validate();

		return zipCode;
	}

	public List<Person> listPersonsByGwa() {
		return personDao.list().stream().sorted((firstPerson, secondPerson) -> 
			firstPerson.getGWA() == null? 1: 
				secondPerson.getGWA() == null? -1:	
					firstPerson.getGWA().compareTo(secondPerson.getGWA())
		).collect(Collectors.toList());
	}

	public List<Person> listPersonsByDateHired() {
		return personDao.listByDateHired();
	}

	public List<Person> listPersonsByLastName() {
		return personDao.listByLastName();
	}
}