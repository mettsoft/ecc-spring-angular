package com.ecc.spring.service;

public interface Service<R> {
	R create(R DTO);
	R update(R DTO);
	R delete(Integer id);
	R get(Integer id);
}