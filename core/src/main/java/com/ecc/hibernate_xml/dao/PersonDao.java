package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.criterion.Order;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.TransactionScope;

public class PersonDao extends AbstractDao<Person> {

	public PersonDao() {
		super(Person.class);
	}

	public List<Person> listByDateHired() {		
		return TransactionScope.executeTransactionWithResult(session -> 
			session.createQuery("FROM Person ORDER BY dateHired").list());
	}

	public List<Person> listByLastName() {
		return TransactionScope.executeTransactionWithResult(session -> {
			return session.createCriteria(Person.class)
				.addOrder(Order.asc("name.lastName"))
				.list();
		});
	}
}