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

	public String validateName(String data, String component) throws ValidationException {
		if (component != "Title" && component != "Suffix") {
			validator.validate("NotEmpty", data, component);		
		}
		validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);
		return data;		
	}

	public <T> T validateAddress(T data, String component) throws ValidationException {
		validator.validate("NotEmpty", data, component);

		if (component == "Street number") {
			validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);		
		}
		else if (component == "Municipality") {
			validator.validate("MaxLength", data, MAX_MUNICIPALITY_CHARACTERS, component);			
		}
		return data;
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