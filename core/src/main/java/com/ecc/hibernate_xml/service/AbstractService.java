package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

import com.ecc.hibernate_xml.dao.Dao;
import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.assembler.Assembler;
import com.ecc.hibernate_xml.model.Entity;
import com.ecc.hibernate_xml.util.app.AssemblerUtils;

public abstract class AbstractService<T extends Entity, R> implements Service<T, R> {
	protected final Dao<T> dao;
	protected final Assembler<T, R> assembler;

	protected AbstractService(Dao<T> dao, Assembler<T, R> assembler) {
		this.dao = dao;
		this.assembler = assembler;
	}

	@Override
	public List<R> list() {
		return AssemblerUtils.asList(dao.list(), assembler::createDTO);
	}

	@Override
	public Serializable create(R dto) throws DaoException {
		T entity = assembler.createModel(dto);
		return dao.create(entity);
	}

	@Override
	public void update(R dto) throws DaoException {
		dao.update(assembler.createModel(dto));
	}

	@Override
	public void delete(Integer id) throws DaoException {
		dao.delete(dao.get(id));
	} 

	@Override
	public R get(Integer id) throws DaoException {
		return assembler.createDTO(dao.get(id));
	}
}