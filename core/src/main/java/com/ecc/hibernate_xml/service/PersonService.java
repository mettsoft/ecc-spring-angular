package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.Date;

import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.assembler.PersonAssembler;
import com.ecc.hibernate_xml.model.Name;
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

	public String validateName(String data, String component) throws ValidationException {
		if (!component.equals("Title") && !component.equals("Suffix")) {
			validator.validate("NotEmpty", data, component);		
		}
		validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);
		return data;		
	}

	public <T> T validateAddress(T data, String component) throws ValidationException {
		validator.validate("NotEmpty", data, component);

		if (component.equals("Street number")) {
			validator.validate("MaxLength", data, DEFAULT_MAX_CHARACTERS, component);		
		}
		else if (component.equals("Municipality")) {
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

	public List<PersonDTO> listPersonsByGwa() {
		return assembler.createDTO(personDao.list().stream().sorted((firstPerson, secondPerson) -> 
			firstPerson.getGWA() == null? 1: 
				secondPerson.getGWA() == null? -1:	
					firstPerson.getGWA().compareTo(secondPerson.getGWA())
		).collect(Collectors.toList()));
	}

	public List<PersonDTO> listPersonsByDateHired() {
		return assembler.createDTO(personDao.listByDateHired());
	}

	public List<PersonDTO> listPersonsByLastName() {
		return assembler.createDTO(personDao.listByLastName());
	}
}