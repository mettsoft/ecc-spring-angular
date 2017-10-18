package com.ecc.hibernate_xml.dto;

import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.dto.RoleDTO;

public class RoleAssembler extends AbstractAssembler<Role, RoleDTO> {
	@Override
	public RoleDTO createDTO(Role role) {
		RoleDTO dto = new RoleDTO();
		dto.setId(role.getId());
		dto.setName(role.getName());
		dto.setPersons(role.getPersons().stream().map(t -> t.getId()).collect(Collectors.toList()));	
		return dto;
	}

	@Override 
	public Role createModel(RoleDTO dto) {
		Role role = new Role();
		role.setId(dto.getId());
		role.setName(dto.getName());
		return role;
	}
}