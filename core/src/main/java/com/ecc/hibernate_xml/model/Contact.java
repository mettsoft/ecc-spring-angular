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

	public String getContactType() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public String toString() {
		return String.format("[ID=%d][%s] %s", id, getContactType(), data);
	}
}