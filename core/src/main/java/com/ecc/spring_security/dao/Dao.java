package com.ecc.spring_security.dao;

import java.io.Serializable;

public interface Dao<T> {
	Serializable create(T entity);
	void update(T entity);
	void delete(T entity);
	T get(Integer id);
}