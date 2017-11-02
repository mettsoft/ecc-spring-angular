package com.ecc.spring_xml.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

import com.ecc.spring_xml.model.Role;

public class RoleDao extends AbstractDao<Role> {
	public RoleDao(SessionFactory sessionFactory) {
		super(Role.class, sessionFactory);
	}

	public List<Role> list() {
		return sessionFactory.getCurrentSession()
			.createCriteria(Role.class)
			.setCacheable(true)
			.addOrder(Order.asc("id"))
			.list();
	}
}