package com.ecc.hibernate_xml.service;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.assembler.PersonAssembler;
import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class PersonService extends AbstractService<Person, PersonDTO> {
	private static final Integer DEFAULT_MAX_CHARACTERS = 20;
	private static final Integer MAX_MUNICIPALITY_CHARACTERS = 50;

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
			validator.validate("MaxLength", data, MAX_MUNICIPALITY_CHARACTERS, component);			
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

	public List<PersonDTO> list(String lastName, Integer roleId, Date birthday, String orderBy, String order) {
		return assembler.createDTO(personDao.list(lastName, roleId, birthday, orderBy, order));		
	}
}