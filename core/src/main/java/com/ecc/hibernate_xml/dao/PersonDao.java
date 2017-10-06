package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class PersonDao extends AbstractDao<Person> {

	public PersonDao() {
		super(Person.class);
	}

	public List<Person> listByDateHired() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("FROM Person ORDER BY dateHired").list();
		session.close();
		return persons;
	}

	public List<Person> listByLastName() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Person> persons = session.createQuery("FROM Person ORDER BY name.lastName").list();
		session.close();
		return persons;
	}
}