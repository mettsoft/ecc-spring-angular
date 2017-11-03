package com.ecc.spring_xml.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataRetrievalFailureException;

public abstract class AbstractDao<T> implements Dao<T> {
	private final Class<T> type;
	protected final SessionFactory sessionFactory;

	protected AbstractDao(Class<T> type, SessionFactory sessionFactory) {
		this.type = type;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Serializable create(T entity) {
		return sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	public void delete(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
	} 

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