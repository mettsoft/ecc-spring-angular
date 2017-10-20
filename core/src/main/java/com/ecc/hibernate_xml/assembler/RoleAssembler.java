package com.ecc.hibernate_xml.assembler;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;

public class RoleAssembler extends AbstractAssembler<Role, RoleDTO> {
	private NameAssembler nameAssembler = new NameAssembler();
	
	@Override
	public RoleDTO createDTO(Role model) {
		if (model == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPersons(createProxyPersonDTO(model.getPersons()));
		return dto;
	}

	private PersonDTO createProxyPersonDTO(Person model) {
		if (model == null) {
			return null;
		}		
		PersonDTO dto = new PersonDTO();
		dto.setId(model.getId());
		dto.setName(nameAssembler.createDTO(model.getName()));
		return dto;
	}

	private List<PersonDTO> createProxyPersonDTO(Set<Person> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::createProxyPersonDTO).collect(Collectors.toList());
	}

	@Override 
	public Role createModel(RoleDTO dto) {
		if (dto == null) {
			return null;
		}
		Role model = new Role();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setPersons(createProxyPersonModel(dto.getPersons()));
		return model;
	}

	private Person createProxyPersonModel(PersonDTO dto) {
		if (dto == null) {
			return null;
		}
		Person model = new Person();
		model.setId(dto.getId());
		model.setName(nameAssembler.createModel(dto.getName()));
		return model;
	}

	private Set<Person> createProxyPersonModel(List<PersonDTO> dtos) {
		if (dtos == null) {
			return null;
		}
		return dtos.stream().map(this::createProxyPersonModel).collect(Collectors.toSet());
	}
}