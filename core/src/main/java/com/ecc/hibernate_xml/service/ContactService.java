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

	public void deleteContact(Contact contact) throws DaoException {
		contactDao.deleteContact(contact);
	}
}