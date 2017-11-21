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
		T entity = createModel(dto);
		try {
			return dao.create(entity);		
		}
		catch (RuntimeException cause) {
			throw onCreateFailure(entity, cause);
		}
	}

  @Transactional
	public void update(R dto) {
		T entity = createModel(dto);
		try {
			dao.update(entity);	
		}
		catch (RuntimeException cause) {
			throw onUpdateFailure(entity, cause);
		}
	}

  @Transactional
	public void delete(Integer id) {
		T entity = createModel(get(id));
		try {
			dao.delete(entity);
		}
		catch (RuntimeException cause) {
			throw onDeleteFailure(entity, cause);
		}
	} 

  @Transactional
	public R get(Integer id) {
		try {
			T entity = dao.get(id);
			return createDTO(entity);
		}
		catch (RuntimeException cause) {
			throw onGetFailure(id, cause);
		}
	}

	public abstract R createDTO(T entity);
	public abstract T createModel(R dto);

	protected RuntimeException onCreateFailure(T entity, RuntimeException cause) { return cause; }
	protected RuntimeException onUpdateFailure(T entity, RuntimeException cause) { return cause; }
	protected RuntimeException onDeleteFailure(T entity, RuntimeException cause) { return cause; }
	protected RuntimeException onGetFailure(Integer id, RuntimeException cause) { return cause; }
}