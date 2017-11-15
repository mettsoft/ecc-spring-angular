package com.ecc.spring_security.dao;

import java.util.List;

import org.hibernate.SessionFactory;
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
			.createQuery("SELECT U FROM User U LEFT JOIN U.permissions P WHERE P.name <> 'ROLE_ADMIN' OR P.name IS NULL GROUP BY U.id ORDER BY U.id")
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