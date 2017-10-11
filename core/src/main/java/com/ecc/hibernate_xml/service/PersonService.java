package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.model.Person;

public class PersonService {
	private PersonDao personDao;

	public PersonService() {
		personDao = new PersonDao();
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

	public void createPerson(Person person) throws DaoException {
		personDao.create(person);		
	}

	public void updatePerson(Person person) throws DaoException {
		personDao.update(person);
	}

	public void deletePerson(Integer personId) throws DaoException {
		personDao.delete(personDao.get(personId));
	}

	public Person getPerson(Integer personId) throws DaoException {
		return personDao.get(personId);
	}
}