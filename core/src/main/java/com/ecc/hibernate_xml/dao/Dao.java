package com.ecc.hibernate_xml.dao;

import java.util.List;
import java.io.Serializable;

import com.ecc.hibernate_xml.model.Entity;

public interface Dao<T> {
	List<T> list();
	Serializable create(T entity) throws DaoException;
	void update(T entity) throws DaoException;
	void delete(T entity) throws DaoException;
	T get(Integer id) throws DaoException;
}