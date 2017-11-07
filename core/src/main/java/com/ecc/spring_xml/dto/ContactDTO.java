package com.ecc.spring_xml.dto;

public class ContactDTO {
	private Integer id;
	private String data;
	private String contactType;

	public Integer getId() {
		return id;
	}

	public String getData() {
		return data;
	}
	
	public String getContactType() {
		return contactType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", contactType, data);
	}
}