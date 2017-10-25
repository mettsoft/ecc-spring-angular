package com.ecc.hibernate_xml.assembler;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.util.app.AssemblerUtils;

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

	private PersonDTO createProxyPersonDTO(Person model) {
		return model == null? null: new PersonDTO(model.getId(), nameAssembler.createDTO(model.getName()));
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

	private Person createProxyPersonModel(PersonDTO dto) {
		return dto == null? null: new Person(dto.getId(), nameAssembler.createModel(dto.getName()));
	}
}