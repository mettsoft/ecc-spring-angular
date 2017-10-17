package com.ecc.hibernate_xml.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ecc.hibernate_xml.util.dao.TransactionScope;

public abstract class AbstractDao<T> implements Dao<T> {

	private final Class<T> type;

	protected AbstractDao(Class<T> type) {
		this.type = type;
	}

	@Override
	public List<T> list() {
		return TransactionScope.executeTransactionWithResult(session -> {
			return session.createCriteria(type)
				.addOrder(Order.asc("id"))
				.list();
		});
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
		catch (Exception cause) {
			throw new DaoException(onDeleteFailure(entity, cause));
		}
	} 

	@Override
	public T get(Integer id) {
		return TransactionScope.executeTransactionWithResult(session -> {
			T entity =  (T) session.get(type, id);
			if (entity == null) {
				throw new RuntimeException(String.format("%s not found!", type.getSimpleName()));
			}
			return entity;			
		});
	}

	protected void onBeforeSave(Session session, T entity) {}
	protected void onBeforeUpdate(Session session, T entity) {}
	protected void onBeforeDelete(Session session, T entity) {}
	protected Throwable onDeleteFailure(T entity, Throwable cause) { return cause; }
}