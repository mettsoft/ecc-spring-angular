package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.Dao;
import com.ecc.hibernate_xml.dao.DaoException;

public abstract class AbstractService<T> implements Service<T> {
	protected final Dao<T> dao;

	protected AbstractService(Dao<T> dao) {
		this.dao = dao;
	}

	@Override
	public List<T> list() {
		return dao.list();
	}

	@Override
	public void create(T entity) throws DaoException {
		dao.create(entity);
	}

	@Override
	public void update(T entity) throws DaoException {
		dao.update(entity);
	}

	@Override
	public void delete(Integer id) throws DaoException {
		dao.delete(dao.get(id));
	} 

	@Override
	public T get(Integer id) throws DaoException {
		return dao.get(id);
	}
}