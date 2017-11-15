package com.ecc.spring_security.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecc.spring_security.model.Permission;

@Repository
public class PermissionDao extends AbstractDao<Permission> {
	public PermissionDao(SessionFactory sessionFactory) {
		super(Permission.class, sessionFactory);
	}

	@Transactional
	public List<Permission> list() {
		return sessionFactory.getCurrentSession()
			.createCriteria(Permission.class)
			.addOrder(Order.asc("id"))
			.list();
	}
}