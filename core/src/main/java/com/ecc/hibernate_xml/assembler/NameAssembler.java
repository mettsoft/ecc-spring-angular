package com.ecc.hibernate_xml.assembler;

import com.ecc.hibernate_xml.model.Name;
import com.ecc.hibernate_xml.dto.NameDTO;

public class NameAssembler implements Assembler<Name, NameDTO> {	
	@Override
	public NameDTO createDTO(Name model) {
		if (model == null) {
			return null;
		}
		NameDTO dto = new NameDTO();
		dto.setTitle(model.getTitle());
		dto.setLastName(model.getLastName());
		dto.setFirstName(model.getFirstName());
		dto.setMiddleName(model.getMiddleName());
		dto.setSuffix(model.getSuffix());
		return dto;
	}

	@Override 
	public Name createModel(NameDTO dto) {
		if (dto == null) {
			return null;
		}
		Name model = new Name();
		model.setTitle(dto.getTitle());
		model.setLastName(dto.getLastName());
		model.setFirstName(dto.getFirstName());
		model.setMiddleName(dto.getMiddleName());
		model.setSuffix(dto.getSuffix());
		return model;
	}
}