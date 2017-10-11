package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.HibernateUtility;

public class ContactDao extends AbstractDao<Contact> {

	public ContactDao() {
		super(Contact.class);
	}

	public List<Contact> list(Person person) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<Contact> contacts = session.createCriteria(Contact.class)
			.add(Restrictions.eq("person.id", person.getId()))
			.addOrder(Order.asc("id"))
			.list();
		session.close();
		return contacts;
	}
}