package com.ecc.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ecc.spring.model.Role;

@Repository
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

	public Role get(String name) {
		return (Role) sessionFactory.getCurrentSession()
			.createCriteria(Role.class)
			.add(Restrictions.eq("name", name))
			.uniqueResult();
	}
}