package com.ecc.spring.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.ecc.spring.dao.Dao;

public abstract class AbstractService<T, R> {
	private final Dao<T> dao;

	protected AbstractService(Dao<T> dao) {
		this.dao = dao;
	}

  @Transactional
	public Serializable create(R dto) {
		return dao.create(createModel(dto));	
	}

  @Transactional
	public void update(R dto) {
		dao.update(createModel(dto));
	}

  @Transactional
	public void delete(Integer id) {
		dao.delete(createModel(get(id)));
	} 

  @Transactional
	public R get(Integer id) {
		return createDTO(dao.get(id));
	}

	public abstract R createDTO(T entity);
	public abstract T createModel(R dto);
}