package com.ecc.spring.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecc.spring.model.Role;
import com.ecc.spring.dto.RoleDTO;
import com.ecc.spring.util.AssemblerUtils;

@Component
public class RoleAssembler implements Assembler<Role, RoleDTO> {
	@Autowired
	private PersonAssembler personAssembler;
	
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