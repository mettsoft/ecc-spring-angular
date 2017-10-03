package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;

import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.HibernateUtility;
import com.ecc.hibernate_xml.util.TransactionScope;

public class ContactDao {

	public List<Contact> listContacts(Person person) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Contact WHERE person_id = :id ORDER BY id");
		query.setParameter("id", person.getId());
		List<Contact> contacts = query.list();
		session.close();
		return contacts;
	}

	public void updateContact(Contact contact) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.update(contact));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	public void deleteContact(Contact contact) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.delete(contact));
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}
}