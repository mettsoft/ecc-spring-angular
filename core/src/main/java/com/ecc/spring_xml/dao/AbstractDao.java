package com.ecc.spring_xml.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractDao<T> implements Dao<T> {
	private final Class<T> type;
	protected final SessionFactory sessionFactory;

	protected AbstractDao(Class<T> type, SessionFactory sessionFactory) {
		this.type = type;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Serializable create(T entity) {
		try {
			return sessionFactory.getCurrentSession().save(entity);		
		}
		catch (Exception cause) {
			throw onCreateFailure(entity, cause);
		}
	}

	@Override
	public void update(T entity) {
		try {
			sessionFactory.getCurrentSession().update(entity);		
		}
		catch (Exception cause) {
			throw onUpdateFailure(entity, cause);
		}
	}

	@Override
	public void delete(T entity) {
		try {
			sessionFactory.getCurrentSession().delete(entity);		
		}
		catch (Exception cause) {
			throw onDeleteFailure(entity, cause);
		}
	} 

	@Override
	public T get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		T entity =  (T) session.get(type, id);
		if (entity == null) {
			throw new RuntimeException(String.format("%s not found!", type.getSimpleName()));
		}
		return entity;			
	}
	
	protected RuntimeException onCreateFailure(T entity, Exception cause) { return toRuntimeException(cause); }
	protected RuntimeException onUpdateFailure(T entity, Exception cause) { return toRuntimeException(cause); }
	protected RuntimeException onDeleteFailure(T entity, Exception cause) { return toRuntimeException(cause); }
	private RuntimeException toRuntimeException(Exception cause) { return (cause instanceof RuntimeException)? (RuntimeException) cause: new RuntimeException(cause); }
}