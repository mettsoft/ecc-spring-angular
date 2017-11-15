package com.ecc.spring_security.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecc.spring_security.model.User;

@Repository
public class UserDao extends AbstractDao<User> {
	public UserDao(SessionFactory sessionFactory) {
		super(User.class, sessionFactory);
	}

	@Transactional
	public List<User> list() {
		return sessionFactory.getCurrentSession()
			.createCriteria(User.class)
			.createAlias("permissions", "P", CriteriaSpecification.LEFT_JOIN)
			.add(
				Restrictions.or(
					Restrictions.ne("P.name", "ROLE_ADMIN"),
					Restrictions.isNull("P.name")
				)
			)
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