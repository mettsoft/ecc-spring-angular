package com.ecc.hibernate_xml.model;

import java.util.Set;

import com.ecc.hibernate_xml.model.Person;

public class Role {
	private Integer id;
	private String name;
	private Set<Person> persons;

	public Role() {

	}

	public Role(String name) {
		setName(name);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	@Override
	public int hashCode() {
		return id.hashCode() << 4 + name.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Role) {
			Role otherRole = (Role) object;
			return otherRole.id.equals(id) && otherRole.name.equals(name);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("------ ID: %d ------\nName: %s", id, name);
	}
}