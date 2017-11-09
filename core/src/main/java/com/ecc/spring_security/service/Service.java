package com.ecc.spring_security.service;

import java.io.Serializable;

public interface Service<R> {
	Serializable create(R DTO);
	void update(R DTO);
	void delete(Integer id);
	R get(Integer id);
}