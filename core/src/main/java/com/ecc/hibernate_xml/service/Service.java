package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;

public interface Service<T, R> {
	List<R> list();
	Serializable create(R DTO) throws DaoException;
	void update(R DTO) throws DaoException;
	void delete(Integer id) throws DaoException;
	R get(Integer id) throws DaoException;
}