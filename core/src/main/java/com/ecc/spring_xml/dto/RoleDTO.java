package com.ecc.spring_xml.dto;

import java.util.List;

public class RoleDTO {
	private Integer id;
	private String name;
	private List<PersonDTO> persons;

	public RoleDTO() {}

	public RoleDTO(Integer id) {
		setId(id);
		setName(id.toString());
	}

	public RoleDTO(Integer id, String name) {
		setId(id);
		setName(name);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersons(List<PersonDTO> persons) {
		this.persons = persons;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public List<PersonDTO> getPersons() {
		return persons;
	}

	@Override
	public String toString() {
		return name;
	}
}