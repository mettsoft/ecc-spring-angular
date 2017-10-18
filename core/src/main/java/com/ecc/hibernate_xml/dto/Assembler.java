package com.ecc.hibernate_xml.dto;

import java.util.List;

public interface Assembler<T, R> {
	R createDTO(T entity);
	T createModel(R dto);
	List<R> createDTO(List<T> entities);
	List<T> createModel(List<R> dtos);
}