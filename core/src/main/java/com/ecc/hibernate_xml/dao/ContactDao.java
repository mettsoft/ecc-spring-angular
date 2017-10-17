package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.dao.TransactionScope;

public class ContactDao extends AbstractDao<Contact> {

	public ContactDao() {
		super(Contact.class);
	}

	@Override
	protected Throwable onCreateFailure(Contact contact, Throwable cause) {
		return onUpdateFailure(contact, cause);
	}

	@Override
	protected Throwable onUpdateFailure(Contact contact, Throwable cause) {
		if (cause instanceof ConstraintViolationException) {
			return new RuntimeException(String.format(
				"Contact \"[%s] %s\" is already existing.", contact.getContactType(), contact.getData()));
		}
		return cause;
	}

	public List<Contact> list(Person person) {
		return TransactionScope.executeTransactionWithResult(session -> {
			return session.createCriteria(Contact.class)
			.add(Restrictions.eq("person.id", person.getId()))
			.addOrder(Order.asc("id"))
			.list();
		});
	}

	public Contact get(Integer id, Person person) {
		return TransactionScope.executeTransactionWithResult(session -> {
			Contact entity = (Contact) session.createCriteria(Contact.class)
				.add(Restrictions.eq("person.id", person.getId()))
				.add(Restrictions.eq("id", id))
				.uniqueResult();
			if (entity == null) {
				throw new RuntimeException("Contact not found!");
			}
			return entity;			
		});
	}
}