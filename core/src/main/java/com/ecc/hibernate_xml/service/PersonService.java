package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class PersonService extends AbstractService<Person> {
	private static final Integer MAX_CHARACTERS = 20;
	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed " + MAX_CHARACTERS + " characters.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";

	private PersonDao personDao;

	public PersonService() {
		super(new PersonDao());
		personDao = (PersonDao) dao;
	}

	public static String validateTitle(String title) throws ValidationException {
		ModelValidator
			.create(title)
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Title"))
			.validate();

		return title;
	}

	public static String validateLastName(String lastName) throws ValidationException {
		ModelValidator
			.create(lastName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Last name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Last name"))
			.validate();

		return lastName;
	}

	public static String validateFirstName(String firstName) throws ValidationException {
		ModelValidator
			.create(firstName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "First name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"First name"))
			.validate();

		return firstName;
	}

	public static String validateMiddleName(String middleName) throws ValidationException {
		ModelValidator
			.create(middleName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Middle name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Middle name"))
			.validate();

		return middleName;
	}

	public static String validateSuffix(String suffix) throws ValidationException {
		ModelValidator
			.create(suffix)
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Suffix"))
			.validate();

		return suffix;
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