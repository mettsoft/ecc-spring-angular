package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Person;

public class PersonService {
	private PersonDao dao;

	public PersonService() {
		dao = new PersonDao();
	}

	public List<Person> listPersonsByGwa() {
		return dao.listPersons().stream().sorted((firstPerson, secondPerson) -> 
			firstPerson.getGWA() == null? 1: 
				secondPerson.getGWA() == null? -1:	
					firstPerson.getGWA().compareTo(secondPerson.getGWA())
		).collect(Collectors.toList());
	}

	public List<Person> listPersonsByDateHired() {
		return dao.listPersonsByDateHired();
	}

	public List<Person> listPersonsByLastName() {
		return dao.listPersonsByLastName();
	}

	public void createPerson(Person person) throws DaoException {
		dao.createPerson(person);		
	}

	public void updatePerson(Person person) throws DaoException {
		dao.updatePerson(person);
	}

	public void deletePerson(Integer personId) throws DaoException {
		dao.deletePerson(personId);
	}

	public Person getPerson(Integer personId) throws DaoException {
		return dao.getPerson(personId);
	}
}