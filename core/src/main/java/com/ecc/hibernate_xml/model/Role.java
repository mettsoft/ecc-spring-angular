package com.ecc.hibernate_xml.model;

public class Role {
	private Integer id;
	private String name;

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

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}