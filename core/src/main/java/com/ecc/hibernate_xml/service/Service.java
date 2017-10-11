package com.ecc.hibernate_xml.service;

import java.util.List;
import java.io.Serializable;
import com.ecc.hibernate_xml.dao.DaoException;

public interface Service<T> {
	public List<T> list();
	public Serializable create(T entity) throws DaoException;
	public void update(T entity) throws DaoException;
	public void delete(Integer id) throws DaoException;
	public T get(Integer id) throws DaoException;
}