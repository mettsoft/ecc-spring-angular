package com.ecc.hibernate_xml.service;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.PersonDao;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.dao.ContactDao;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Contact;

public class PersonService {
	private PersonDao personDao;
	private RoleDao roleDao;
	private ContactDao contactDao;

	public PersonService() {
		personDao = new PersonDao();
		roleDao = new RoleDao();
		contactDao = new ContactDao();
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

	public void addRoleToPerson(Integer roleId, Person person) throws DaoException {
		person.getRoles().add(roleDao.get(roleId));
		personDao.update(person);
	}

	public void removeRoleFromPerson(Integer roleId, Person person) throws DaoException {
		person.getRoles().remove(roleDao.get(roleId));
		personDao.update(person);
	}

	public void addContactToPerson(Contact contact, Person person) throws DaoException {
		person.getContacts().add(contact);
		personDao.update(person);
	}

	public void removeContactFromPerson(Integer contactId, Person person) throws DaoException {
		Contact contact = contactDao.get(contactId);
		person.getContacts().remove(contact);
		personDao.update(person);
	}
}