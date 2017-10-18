package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.Dao;
import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dto.Assembler;

public abstract class AbstractService<T, R> implements Service<T, R> {
	protected final Dao<T> dao;
	protected final Assembler<T, R> assembler;

	// TODO: Remove this.
	protected AbstractService(Dao<T> dao) {
		this.dao = dao;
		this.assembler = null;
	}

	protected AbstractService(Dao<T> dao, Assembler<T, R> assembler) {
		this.dao = dao;
		this.assembler = assembler;
	}

	@Override
	public List<R> list() {
		return assembler.createDTO(dao.list());
	}

	@Override
	public void create(R DTO) throws DaoException {
		dao.create(assembler.createModel(DTO));
	}

	@Override
	public void update(R DTO) throws DaoException {
		dao.update(assembler.createModel(DTO));
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