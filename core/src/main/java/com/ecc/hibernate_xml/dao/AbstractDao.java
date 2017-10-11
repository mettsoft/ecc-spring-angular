package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ecc.hibernate_xml.util.HibernateUtility;
import com.ecc.hibernate_xml.util.TransactionScope;

public abstract class AbstractDao<T> implements Dao<T> {

	private final Class<T> type;

	protected AbstractDao(Class<T> type) {
		this.type = type;
	}

	@Override
	public List<T> list() {
		Session session = HibernateUtility.getSessionFactory().openSession();
		List<T> objects = session.createCriteria(type)
			.addOrder(Order.asc("id"))
			.list();
		session.close();
		return objects;
	}

	@Override
	public void create(T entity) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> {
				onBeforeSave(session, entity);
				session.persist(entity);
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