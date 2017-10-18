package com.ecc.hibernate_xml.assembler;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Name;
import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.dto.PersonDTO;

public class RoleAssembler extends AbstractAssembler<Role, RoleDTO> {
	@Override
	public RoleDTO createDTO(Role model) {
		if (model == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setPersons(createProxyDTO(model.getPersons()));
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

	private List<PersonDTO> createProxyDTO(Set<Person> models) {
		if (models == null) {
			return null;
		}
		return models.stream().map(this::createProxyDTO).collect(Collectors.toList());
	}

	@Override 
	public Role createModel(RoleDTO dto) {
		if (dto == null) {
			return null;
		}
		Role model = new Role();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setPersons(createProxyModel(dto.getPersons()));
		return model;
	}

	private Person createProxyModel(PersonDTO dto) {
		if (dto == null) {
			return null;
		}
		Person model = new Person();
		Name name = new Name();
		name.setLastName(dto.getId().toString());
		name.setMiddleName(dto.getId().toString());
		name.setFirstName(dto.getId().toString());
		model.setId(dto.getId());
		model.setName(name);
		return model;
	}

	private Set<Person> createProxyModel(List<PersonDTO> dtos) {
		if (dtos == null) {
			return null;
		}
		return dtos.stream().map(this::createProxyModel).collect(Collectors.toSet());
	}
}