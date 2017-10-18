package com.ecc.hibernate_xml.dto;

import java.util.List;

public interface Assembler<T, R> {
	R createDTO(T entity);
	T createModel(R DTO);
	List<R> createDTO(List<T> entity);
	List<T> createModel(List<R> DTO);
}