package com.ecc.hibernate_xml.dto;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAssembler<T, R> implements Assembler<T, R> {
	@Override
	public List<R> createDTO(List<T> entities) {
		return entities.stream().map(this::createDTO).collect(Collectors.toList());
	}

	@Override
	public List<T> createModel(List<R> dtos) {
		return dtos.stream().map(this::createModel).collect(Collectors.toList());
	}
}