package com.ecc.spring_xml.dao;

import java.util.List;
import java.io.Serializable;

public interface Dao<T> {
	Serializable create(T entity) throws DaoException;
	void update(T entity) throws DaoException;
	void delete(T entity) throws DaoException;
	T get(Integer id) throws DaoException;
}