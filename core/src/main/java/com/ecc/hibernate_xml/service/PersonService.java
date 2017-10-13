package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.Date;

import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Name;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class PersonService extends AbstractService<Person> {
	private static final Integer DEFAULT_MAX_CHARACTERS = 20;
	private static final Integer MAX_MUNICIPALITY_CHARACTERS = 50;

	private final PersonDao personDao;
	private final ModelValidator validator;

	public PersonService() {
		super(new PersonDao());
		personDao = (PersonDao) dao;
		validator = ModelValidator.create();
	}

	public String validateTitle(String title) throws ValidationException {
		validator.validate("MaxLength", title, DEFAULT_MAX_CHARACTERS, "Title");
		return title;
	}

	public String validateName(String name, String nameType) throws ValidationException {
		validator.validate("NotEmpty", name, nameType);
		validator.validate("MaxLength", name, DEFAULT_MAX_CHARACTERS, nameType);
		return name;		
	}

	public String validateSuffix(String suffix) throws ValidationException {
		validator.validate("MaxLength", suffix, DEFAULT_MAX_CHARACTERS, "Suffix");
		return suffix;
	}

	public String validateStreetNumber(String streetNumber) throws ValidationException {
		validator.validate("NotEmpty", streetNumber, "Street number");
		validator.validate("MaxLength", streetNumber, DEFAULT_MAX_CHARACTERS, "Street number");
		return streetNumber;
	}

	public Integer validateBarangay(Integer barangay) throws ValidationException {
		validator.validate("NotNull", barangay, "Barangay");
		return barangay;
	}

	public String validateMunicipality(String municipality) throws ValidationException {
		validator.validate("NotEmpty", municipality, "Municipality");
		validator.validate("MaxLength", municipality, MAX_MUNICIPALITY_CHARACTERS, "Municipality");
		return municipality;
	}

	public Integer validateZipCode(Integer zipCode) throws ValidationException {
		validator.validate("NotNull", zipCode, "Zip code");
		return zipCode;
	}

	public BigDecimal validateGWA(BigDecimal GWA) throws ValidationException {
		validator.validate("Minimum", GWA, 1, "GWA");
		validator.validate("Maximum", GWA, 5, "GWA");
		return GWA;
	}

	public Date validateDateHired(Boolean currentlyEmployed, Date dateHired) throws ValidationException {
		if (!currentlyEmployed && dateHired != null) {
			throw new ValidationException("Date hired cannot be assigned if person is unemployed.");
		}
		else if (currentlyEmployed && dateHired == null) {
			throw new ValidationException("Date hired cannot be null if person is employed.");
		}
		return dateHired;
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