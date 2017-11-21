package com.ecc.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.ecc.spring.model.Permission;

@Repository
public class PermissionDao extends AbstractDao<Permission> {
	public PermissionDao(SessionFactory sessionFactory) {
		super(Permission.class, sessionFactory);
	}

	public List<Permission> list() {
		return sessionFactory.getCurrentSession()
			.createCriteria(Permission.class)
			.addOrder(Order.asc("id"))
			.list();
	}
}