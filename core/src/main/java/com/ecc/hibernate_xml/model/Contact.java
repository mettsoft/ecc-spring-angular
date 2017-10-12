package com.ecc.hibernate_xml.model;

public abstract class Contact {
	private Integer id;
	private String data;
	private Person person;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getId() {
		return id;
	}

	public String getData() {
		return data;
	}

	public Person getPerson() {
		return person;
	}
	
	public String getContactType() {
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Contact) {
			Contact otherContact = (Contact) object; 
			return id.equals(otherContact.id) && data.equals(otherContact.data)
				&& getContactType().equals(otherContact.getContactType());
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ID=%d][%s] %s", id, getContactType(), data);
	}
}