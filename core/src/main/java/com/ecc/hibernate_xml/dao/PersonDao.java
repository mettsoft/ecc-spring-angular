package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.TransactionScope;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class PersonDao {

	public List<Person> listPersons() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("FROM Person").list();
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

	public void createPerson(Person newPerson) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.save(newPerson));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void updatePerson(Person newPerson) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.update(newPerson));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void deletePerson(Integer personId) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> 
				session.delete(session.get(Person.class, personId)));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public Person getPerson(Integer personId) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Person person = (Person) session.get(Person.class, personId);
		session.close();
		return person;
	}
}