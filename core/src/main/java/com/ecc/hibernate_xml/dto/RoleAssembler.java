package com.ecc.hibernate_xml.dto;

import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.dto.RoleDTO;

public class RoleAssembler extends AbstractAssembler<Role, RoleDTO> {
	@Override
	public RoleDTO createDTO(Role model) {
		if (model == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPersons(model.getPersons().stream().map(this::createProxyDTO).collect(Collectors.toList()));	
		return dto;
	}

	private PersonDTO createProxyDTO(Person model) {
		if (model == null) {
			return null;
		}
		PersonDTO dto = new PersonDTO();
		dto.setId(model.getId());
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
		model.setPersons(dto.getPersons().stream().map(this::createProxyModel).collect(Collectors.toSet()));	
		return model;
	}

	private Person createProxyModel(PersonDTO dto) {
		if (dto == null) {
			return null;
		}
		Person model = new Person();
		model.setId(dto.getId());
		return model;
	}
}