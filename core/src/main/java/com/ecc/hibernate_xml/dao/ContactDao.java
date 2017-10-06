package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;

import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class ContactDao extends AbstractDao<Contact> {

	public ContactDao() {
		super(Contact.class);
	}

	public List<Contact> list(Person person) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Contact WHERE person_id = :id ORDER BY id");
		query.setParameter("id", person.getId());
		List<Contact> contacts = query.list();
		session.close();
		return contacts;
	}
}