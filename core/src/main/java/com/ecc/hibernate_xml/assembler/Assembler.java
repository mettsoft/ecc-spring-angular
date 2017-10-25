package com.ecc.hibernate_xml.assembler;

public interface Assembler<T, R> {
	R createDTO(T entity);
	T createModel(R dto);
}