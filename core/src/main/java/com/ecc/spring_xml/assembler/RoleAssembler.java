package com.ecc.spring_xml.assembler;

import com.ecc.spring_xml.model.Role;
import com.ecc.spring_xml.model.Person;
import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.dto.PersonDTO;
import com.ecc.spring_xml.util.AssemblerUtils;

public class RoleAssembler implements Assembler<Role, RoleDTO> {
	private NameAssembler nameAssembler = new NameAssembler();
	
	@Override
	public RoleDTO createDTO(Role model) {
		if (model == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPersons(AssemblerUtils.asList(model.getPersons(), this::createProxyPersonDTO));
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
		model.setPersons(AssemblerUtils.asSet(dto.getPersons(), this::createProxyPersonModel));
		return model;
	}

	private PersonDTO createProxyPersonDTO(Person model) {
		return model == null? null: new PersonDTO(model.getId(), nameAssembler.createDTO(model.getName()));
	}

	private Person createProxyPersonModel(PersonDTO dto) {
		return dto == null? null: new Person(dto.getId(), nameAssembler.createModel(dto.getName()));
	}
}