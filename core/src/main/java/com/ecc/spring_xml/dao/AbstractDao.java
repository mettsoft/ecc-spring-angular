package com.ecc.spring_xml.dao;

import java.io.Serializable;

import org.springframework.dao.DataAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractDao<T> implements Dao<T> {
	private final Class<T> type;
	private final SessionFactory sessionFactory;

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
			throw new DataAccessException(onCreateFailure(entity, cause));
		}
	}

	@Override
	public void update(T entity) {
		try {
			sessionFactory.getCurrentSession().update(entity);		
		}
		catch (Exception cause) {
			throw new DataAccessException(onUpdateFailure(entity, cause));
		}
	}

	@Override
	public void delete(T entity) {
		try {
			sessionFactory.getCurrentSession().delete(entity);		
		}
		catch (Exception cause) {
			throw new DataAccessException(onDeleteFailure(entity, cause));
		}
	} 

	@Override
	public T get(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		T entity =  (T) session.get(type, id);
		if (entity == null) {
			throw new DataAccessException(String.format("%s not found!", type.getSimpleName()));
		}
		return entity;			
	}
	
	protected Throwable onCreateFailure(T entity, Throwable cause) { return cause; }
	protected Throwable onUpdateFailure(T entity, Throwable cause) { return cause; }
	protected Throwable onDeleteFailure(T entity, Throwable cause) { return cause; }
}