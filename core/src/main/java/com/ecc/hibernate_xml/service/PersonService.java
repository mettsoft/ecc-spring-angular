package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Role;

public class PersonService {
	private PersonDao personDao;
	private RoleDao roleDao;

	public PersonService() {
		personDao = new PersonDao();
		roleDao = new RoleDao();
	}

	public List<Person> listPersonsByGwa() {
		return personDao.listPersons().stream().sorted((firstPerson, secondPerson) -> 
			firstPerson.getGWA() == null? 1: 
				secondPerson.getGWA() == null? -1:	
					firstPerson.getGWA().compareTo(secondPerson.getGWA())
		).collect(Collectors.toList());
	}

	public List<Person> listPersonsByDateHired() {
		return personDao.listPersonsByDateHired();
	}

	public List<Person> listPersonsByLastName() {
		return personDao.listPersonsByLastName();
	}

	public void createPerson(Person person) throws DaoException {
		personDao.createPerson(person);		
	}

	public void updatePerson(Person person) throws DaoException {
		personDao.updatePerson(person);
	}

	public void deletePerson(Integer personId) throws DaoException {
		personDao.deletePerson(personId);
	}

	public Person getPerson(Integer personId) throws DaoException {
		return personDao.getPerson(personId);
	}

	public void addRoleToPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.getRole(roleId);
		person.getRoles().add(role);
		personDao.updatePerson(person);
	}

	public void removeRoleFromPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.getRole(roleId);
		person.getRoles().remove(role);
		personDao.updatePerson(person);
	}
}