package com.ecc.hibernate_xml.model;

public class Name {
	private String title;
	private String lastName;
	private String firstName;
	private String middleName;
	private String suffix;

	public Name() {

	}
	
	public Name(String lastName, String firstName, String middleName) {
		setLastName(lastName);
		setFirstName(firstName);
		setMiddleName(middleName);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTitle() {
		return title;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getSuffix() {
		return suffix;
	}
}