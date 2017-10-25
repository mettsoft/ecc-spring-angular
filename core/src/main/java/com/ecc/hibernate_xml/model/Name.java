package com.ecc.hibernate_xml.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name {
	private String title;
	private String lastName;
	private String firstName;
	private String middleName;
	private String suffix;

	public String getTitle() {
		return title;
	}

	@Column(name="last_name", nullable=false)
	public String getLastName() {
		return lastName;
	}

	@Column(name="first_name", nullable=false)
	public String getFirstName() {
		return firstName;
	}

	@Column(name="middle_name", nullable=false)
	public String getMiddleName() {
		return middleName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setTitle(String title) {
		this.title = title != null && title.trim().isEmpty()? null: title;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName)  {
		this.middleName = middleName;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix != null && suffix.trim().isEmpty()? null: suffix;
	}

	@Override
	public int hashCode() {
		int accumulator = firstName.hashCode() << 8 + 
			middleName.hashCode() << 4 + 
			lastName.hashCode() << 2;
			 
		if (title != null) {
			accumulator += title.hashCode() << 1;
		}

		if (suffix != null) {
			accumulator += suffix.hashCode();
		}

		return accumulator;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Name) {
			Name other = (Name) object; 

			return (title == null && title == other.title || title != null && title.equals(other.title)) && 
				firstName.equals(other.firstName) && 
				middleName.equals(other.middleName) && 
				lastName.equals(other.lastName) && 
				(suffix == null && suffix == other.suffix || suffix != null && suffix.equals(other.suffix));
		}
		return false;
	}
}