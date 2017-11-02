package com.ecc.spring_xml.service;

import java.io.Serializable;

import com.ecc.spring_xml.dao.Dao;
import com.ecc.spring_xml.assembler.Assembler;

public abstract class AbstractService<T, R> implements Service<R> {
	private final Dao<T> dao;
	private final Assembler<T, R> assembler;

	protected AbstractService(Dao<T> dao, Assembler<T, R> assembler) {
		this.dao = dao;
		this.assembler = assembler;
	}

	@Override
	public Serializable create(R dto) {
		T entity = assembler.createModel(dto);
		try {
			return dao.create(entity);		
		}
		catch (RuntimeException cause) {
			throw onCreateFailure(entity, cause);
		}
	}

	@Override
	public void update(R dto) {
		T entity = assembler.createModel(dto);
		try {
			dao.update(entity);	
		}
		catch (RuntimeException cause) {
			throw onUpdateFailure(entity, cause);
		}
	}

	@Override
	public void delete(Integer id) {
		T entity = dao.get(id);
		try {
			dao.delete(entity);
		}
		catch (RuntimeException cause) {
			throw onDeleteFailure(entity, cause);
		}
	} 

	@Override
	public R get(Integer id) {
		return assembler.createDTO(dao.get(id));
	}

	protected RuntimeException onCreateFailure(T entity, RuntimeException cause) { return cause; }
	protected RuntimeException onUpdateFailure(T entity, RuntimeException cause) { return cause; }
	protected RuntimeException onDeleteFailure(T entity, RuntimeException cause) { return cause; }
}