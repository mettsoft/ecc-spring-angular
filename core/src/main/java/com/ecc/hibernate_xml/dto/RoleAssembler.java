package com.ecc.hibernate_xml.dto;

import java.util.List;

import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.model.Role;

public class RoleAssembler implements Assembler<Role, RoleDTO> {
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

	@Override
	public List<RoleDTO> createDTO(List<Role> role) {
		return role.stream().map(this::createDTO).collect(Collectors.toList());
	}

	@Override 
	public List<Role> createModel(List<RoleDTO> dto) {
		return dto.stream().map(this::createModel).collect(Collectors.toList());
	}
}