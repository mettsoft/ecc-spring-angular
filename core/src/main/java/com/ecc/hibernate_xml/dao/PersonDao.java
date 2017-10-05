package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.TransactionScope;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class PersonDao {

	public List<Person> listPersons() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("FROM Person ORDER BY id").list();
		session.close();
		return persons;
	}

	public List<Person> listPersonsByDateHired() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("FROM Person ORDER BY dateHired").list();
		session.close();
		return persons;
	}

	public List<Person> listPersonsByLastName() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("FROM Person ORDER BY name.lastName").list();
		session.close();
		return persons;
	}

	public void createPerson(Person person) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.save(person));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void updatePerson(Person person) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.update(person));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void deletePerson(Person person) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.delete(person));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public Person getPerson(Integer personId) throws DaoException {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Person person = (Person) session.get(Person.class, personId);
		session.close();
		if (person == null) {
			throw new DaoException("Person not found!");
		}
		return person;
	}
}