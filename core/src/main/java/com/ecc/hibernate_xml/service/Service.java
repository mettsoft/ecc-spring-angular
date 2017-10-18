package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;

public interface Service<T, R> {
	public List<R> list();
	public Serializable create(R DTO) throws DaoException;
	public void update(R DTO) throws DaoException;
	public void delete(Integer id) throws DaoException;
	public R get(Integer id) throws DaoException;
}