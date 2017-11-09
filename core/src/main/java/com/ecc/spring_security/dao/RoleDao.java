package com.ecc.spring_security.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ecc.spring_security.model.Role;

@Component
public class RoleDao extends AbstractDao<Role> {
	public RoleDao(SessionFactory sessionFactory) {
		super(Role.class, sessionFactory);
	}

	@Transactional
	public List<Role> list() {
		return sessionFactory.getCurrentSession()
			.createCriteria(Role.class)
			.setCacheable(true)
			.addOrder(Order.asc("id"))
			.list();
	}

	@Transactional
	public Role get(String name) {
		return (Role) sessionFactory.getCurrentSession()
			.createCriteria(Role.class)
			.add(Restrictions.eq("name", name))
			.uniqueResult();
	}
}