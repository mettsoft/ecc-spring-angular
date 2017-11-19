package com.ecc.spring.dao;

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
	public T create(T entity) {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
		return entity;
	}

	@Override
	public T delete(T entity) {
		sessionFactory.getCurrentSession().delete(entity);
		return entity;
	} 

	@Override
	public T get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		T entity =  (T) session.get(type, id);
		if (entity == null) {
			throw new DataRetrievalFailureException(String.format("%s not found!", type.getSimpleName()));
		}
		sessionFactory.getCurrentSession().evict(entity);
		return entity;
	}
}