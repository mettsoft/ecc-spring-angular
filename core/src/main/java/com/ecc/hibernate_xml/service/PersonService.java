package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Person;

public class PersonService extends AbstractService<Person> {
	private PersonDao personDao;

	public PersonService() {
		super(new PersonDao());
		personDao = (PersonDao) dao;
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