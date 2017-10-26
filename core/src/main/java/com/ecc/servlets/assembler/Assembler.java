package com.ecc.servlets.assembler;

public interface Assembler<T, R> {
	R createDTO(T entity);
	T createModel(R dto);
}