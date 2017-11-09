package com.ecc.spring_security.assembler;

public interface Assembler<T, R> {
	R createDTO(T entity);
	T createModel(R dto);
}