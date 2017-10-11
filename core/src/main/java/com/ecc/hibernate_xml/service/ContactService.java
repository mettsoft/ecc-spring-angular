package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.ContactDao;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;

public class ContactService extends AbstractService<Contact> {
	private ContactDao contactDao;

	public ContactService() {
		super(new ContactDao());
		contactDao = (ContactDao) dao;
	}

	public List<Contact> list(Person person) {
		return contactDao.list(person);
	}

	public Serializable create(Contact contact, Person person) throws DaoException {
		contact.setPerson(person);
		return contactDao.create(contact);
	}
}