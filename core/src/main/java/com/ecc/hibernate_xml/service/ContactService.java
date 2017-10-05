package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.ContactDao;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;

public class ContactService {
	private ContactDao contactDao;

	public ContactService() {
		contactDao = new ContactDao();
	}

	public List<Contact> listContacts(Person person) {
		return contactDao.listContacts(person);
	}

	public void updateContact(Contact contact) throws DaoException {
		contactDao.updateContact(contact);
	}

	public void deleteContact(Integer contactId) throws DaoException {
		contactDao.deleteContact(contactDao.getContact(contactId));
	}

	public Contact getContact(Integer contactId) throws DaoException {
		return contactDao.getContact(contactId);
	}
}