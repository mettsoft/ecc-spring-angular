package com.ecc.hibernate_xml.dao;

import java.util.List;
import java.io.Serializable;
import org.hibernate.Session;

import com.ecc.hibernate_xml.util.HibernateUtility;
import com.ecc.hibernate_xml.util.TransactionScope;

public abstract class AbstractDao<T> implements Dao<T> {

	private final Class<T> type;

	protected AbstractDao(Class<T> type) {
		this.type = type;
	}

	@Override
	public List<T> list() {
		final String query = String.format("FROM %s ORDER BY id", type.getSimpleName());
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<T> objects = session.createQuery(query).list();
		session.close();
		return objects;
	}

	@Override
	public Serializable create(T entity) throws DaoException {
		try {
			return TransactionScope.executeTransactionWithResult(session -> {
				onBeforeSave(session, entity);
				return session.save(entity);
			});			
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	@Override
	public void update(T entity) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> {
				onBeforeUpdate(session, entity);
				session.update(entity);				
			});			
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	}

	@Override
	public void delete(T entity) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> {
				onBeforeDelete(session, entity);
				session.delete(entity);				
			});			
		}
		catch (Exception exception) {
			throw new DaoException(exception);
		}
	} 

	@Override
	public T get(Integer id) throws DaoException {
		Session session = HibernateUtility.getSessionFactory().openSession();
		T entity = (T) session.get(type, id);
		session.close();
		if (entity == null) {
			throw new DaoException(String.format("%s not found!", type.getSimpleName()));
		}
		return entity;
	}

	protected void onBeforeSave(Session session, T entity) {}
	protected void onBeforeUpdate(Session session, T entity) {}
	protected void onBeforeDelete(Session session, T entity) {}
}