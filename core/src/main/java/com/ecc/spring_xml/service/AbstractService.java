package com.ecc.spring_xml.service;

import java.io.Serializable;

import com.ecc.spring_xml.dao.Dao;
import com.ecc.spring_xml.assembler.Assembler;
import com.ecc.spring_xml.util.app.AssemblerUtils;

public abstract class AbstractService<T, R> implements Service<R> {
	protected final Dao<T> dao;
	protected final Assembler<T, R> assembler;

	protected AbstractService(Dao<T> dao, Assembler<T, R> assembler) {
		this.dao = dao;
		this.assembler = assembler;
	}

	@Override
	public Serializable create(R dto) {
		return dao.create(assembler.createModel(dto));
	}

	@Override
	public void update(R dto) {
		dao.update(assembler.createModel(dto));
	}

	@Override
	public void delete(Integer id) {
		dao.delete(dao.get(id));
	} 

	@Override
	public R get(Integer id) {
		return assembler.createDTO(dao.get(id));
	}
}