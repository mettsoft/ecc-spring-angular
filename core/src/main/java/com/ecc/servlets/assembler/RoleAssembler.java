package com.ecc.servlets.assembler;

import com.ecc.servlets.model.Role;
import com.ecc.servlets.model.Person;
import com.ecc.servlets.dto.RoleDTO;
import com.ecc.servlets.dto.PersonDTO;
import com.ecc.servlets.util.app.AssemblerUtils;

public class RoleAssembler implements Assembler<Role, RoleDTO> {
	private NameAssembler nameAssembler = new NameAssembler();
	
	@Override
	public RoleDTO createDTO(Role model) {
		if (model == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO(model.getId(), model.getName());
		dto.setPersons(AssemblerUtils.asList(model.getPersons(), this::createProxyPersonDTO));
		return dto;
	}

	@Override 
	public Role createModel(RoleDTO dto) {
		if (dto == null) {
			return null;
		}
		Role model = new Role(dto.getId(), dto.getName());
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