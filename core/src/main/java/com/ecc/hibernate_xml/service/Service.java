package com.ecc.hibernate_xml.service;

import java.io.Serializable;

public interface Service<T, R> {
	Serializable create(R DTO) throws Exception;
	void update(R DTO) throws Exception;
	void delete(Integer id) throws Exception;
	R get(Integer id) throws Exception;
}