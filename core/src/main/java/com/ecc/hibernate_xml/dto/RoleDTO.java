package com.ecc.hibernate_xml.dto;

import java.util.List;

public class RoleDTO {
	private Integer id;
	private String name;
	private List<Integer> personIds;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersons(List<Integer> personIds) {
		this.personIds = personIds;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public List<Integer> getPersons() {
		return personIds;
	}

	@Override
	public String toString() {
		return String.format("[ID=%d] %s", id, name);
	}
}