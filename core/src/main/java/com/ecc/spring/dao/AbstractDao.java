package com.ecc.spring.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDao<T> implements Dao<T> {
	private final Class<T> type;
	protected final SessionFactory sessionFactory;

	protected AbstractDao(Class<T> type, SessionFactory sessionFactory) {
		this.type = type;
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@Override
	public Serializable create(T entity) {
		return sessionFactory.getCurrentSession().save(entity);
	}

	@Transactional
	@Override
	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Transactional
	@Override
	public void delete(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	} 

	@Transactional
	@Override
	public T get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		T entity =  (T) session.get(type, id);
		if (entity == null) {
			throw new DataRetrievalFailureException(String.format("%s not found!", type.getSimpleName()));
		}
		return entity;
	}
}