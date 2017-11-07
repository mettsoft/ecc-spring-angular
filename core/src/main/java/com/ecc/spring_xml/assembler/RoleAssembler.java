package com.ecc.spring_xml.assembler;

import com.ecc.spring_xml.model.Role;
import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.util.AssemblerUtils;

public class RoleAssembler implements Assembler<Role, RoleDTO> {
	private PersonAssembler personAssembler;

	public void setPersonAssembler(PersonAssembler personAssembler) {
		this.personAssembler = personAssembler;
	}
	
	@Override
	public RoleDTO createDTO(Role model) {
		if (model == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPersons(AssemblerUtils.asList(model.getPersons(), personAssembler::createBasicDTO));
		return dto;
	}

	@Override 
	public Role createModel(RoleDTO dto) {
		if (dto == null) {
			return null;
		}
		Role model = new Role();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setPersons(AssemblerUtils.asSet(dto.getPersons(), personAssembler::createBasicModel));
		return model;
	}
}