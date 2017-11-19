package com.ecc.spring.service;

import org.springframework.transaction.annotation.Transactional;

import com.ecc.spring.dao.Dao;

public abstract class AbstractService<T, R> {
	private final Dao<T> dao;

	protected AbstractService(Dao<T> dao) {
		this.dao = dao;
	}

  @Transactional
	public R create(R dto) {
		return createDTO(dao.create(createModel(dto)));	
	}

  @Transactional
	public R update(R dto) {
		return createDTO(dao.update(createModel(dto)));
	}

  @Transactional
	public R delete(Integer id) {
		return createDTO(dao.delete(dao.get(id)));
	} 

  @Transactional
	public R get(Integer id) {
		return createDTO(dao.get(id));
	}

	public abstract R createDTO(T entity);
	public abstract T createModel(R dto);
}