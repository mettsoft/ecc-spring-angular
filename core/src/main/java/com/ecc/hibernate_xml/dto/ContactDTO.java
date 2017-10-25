package com.ecc.hibernate_xml.dto;

public class ContactDTO {
	private Integer id;
	private String data;
	private PersonDTO person;
	private String contactType;

	public ContactDTO() {

	}

	public ContactDTO(String contactType, String data) {
		setContactType(contactType);
		setData(data);
	}
	
	public Integer getId() {
		return id;
	}

	public String getData() {
		return data;
	}

	public PersonDTO getPerson() {
		return person;
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

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", getContactType(), data);
	}
}