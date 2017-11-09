package com.ecc.spring_xml.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ecc.spring_xml.model.User;

@Component
public class UserDao extends AbstractDao<User> {
	public UserDao(SessionFactory sessionFactory) {
		super(User.class, sessionFactory);
	}

	@Transactional
	public List<User> list() {
		return sessionFactory.getCurrentSession()
			.createCriteria(User.class)
			.add(Restrictions.isNotNull("permissions"))
			.addOrder(Order.asc("id"))
			.list();
	}

	@Transactional
	public User get(String username) {
		return (User) sessionFactory.getCurrentSession()
			.createCriteria(User.class)
			.add(Restrictions.eq("username", username))
			.uniqueResult();
	}
}