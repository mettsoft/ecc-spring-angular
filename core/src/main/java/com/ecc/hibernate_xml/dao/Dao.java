package com.ecc.hibernate_xml.dao;

import java.util.List;

public interface Dao<T> {
	public List<T> list();
	public void create(T entity) throws DaoException;
	public void update(T entity) throws DaoException;
	public void delete(T entity) throws DaoException;
	public T get(Integer id) throws DaoException;
}