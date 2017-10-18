package com.ecc.hibernate_xml.dto;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractAssembler<T, R> implements Assembler<T, R> {
	@Override
	public List<R> createDTO(List<T> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::createDTO).collect(Collectors.toList());
	}

	@Override
	public List<T> createModel(List<R> dtos) {
		if (dtos == null) {
			return null;
		}
		return dtos.stream().map(this::createModel).collect(Collectors.toList());
	}
}