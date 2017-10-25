package com.ecc.hibernate_xml.dao;

import java.io.Serializable;
import java.util.List;

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
				.setCacheable(true)
				.addOrder(Order.asc("id"))
				.list();
		});
	}

	@Override
	public Serializable create(T entity) throws DaoException {
		try {
			return TransactionScope.executeTransactionWithResult(session -> session.save(entity));			
		}
		catch (Exception cause) {
			throw new DaoException(onCreateFailure(entity, cause));
		}
	}

	@Override
	public void update(T entity) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.update(entity));			
		}
		catch (Exception cause) {
			throw new DaoException(onUpdateFailure(entity, cause));
		}
	}

	@Override
	public void delete(T entity) throws DaoException {
		try {
			TransactionScope.executeTransaction(session -> session.delete(entity));			
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
	
	protected Throwable onCreateFailure(T entity, Throwable cause) { return cause; }
	protected Throwable onUpdateFailure(T entity, Throwable cause) { return cause; }
	protected Throwable onDeleteFailure(T entity, Throwable cause) { return cause; }
}