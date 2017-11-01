package com.ecc.spring_xml.assembler;

public interface Assembler<T, R> {
	R createDTO(T entity);
	T createModel(R dto);
}