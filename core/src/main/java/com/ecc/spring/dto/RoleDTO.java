package com.ecc.spring.dto;

import java.util.List;
import java.util.ArrayList;

public class RoleDTO {
	private Integer id;
	private String name;
	private List<PersonDTO> persons;

	public RoleDTO() {
		persons = new ArrayList<>();
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