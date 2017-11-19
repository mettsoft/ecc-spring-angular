package com.ecc.spring.dao;

public interface Dao<T> {
	T create(T entity);
	T update(T entity);
	T delete(T entity);
	T get(Integer id);
}