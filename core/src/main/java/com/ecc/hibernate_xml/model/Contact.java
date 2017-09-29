package com.ecc.hibernate_xml.model;

public abstract class Contact {
	private Integer id;
	private String data;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public String getData() {
		return data;
	}
}