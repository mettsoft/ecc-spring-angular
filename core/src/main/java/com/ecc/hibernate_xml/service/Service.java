package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

public interface Service<T, R> {
	List<R> list();
	Serializable create(R DTO) throws Exception;
	void update(R DTO) throws Exception;
	void delete(Integer id) throws Exception;
	R get(Integer id) throws Exception;
}