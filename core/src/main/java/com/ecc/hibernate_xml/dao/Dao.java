package com.ecc.hibernate_xml.dao;

import java.util.List;
import java.io.Serializable;

public interface Dao<T> {
	public List<T> list();
	public Serializable create(T entity) throws DaoException;
	public void update(T entity) throws DaoException;
	public void delete(T entity) throws DaoException;
	public T get(Integer id) throws DaoException;
}