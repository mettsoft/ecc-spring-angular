package com.ecc.spring_xml.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;

@Embeddable
public class Name {
	@Column(name="title")
	private String title;

	@Column(name="last_name", nullable=false)
	private String lastName;

	@Column(name="first_name", nullable=false)
	private String firstName;

	@Column(name="middle_name", nullable=false)
	private String middleName;

	@Column(name="suffix")
	private String suffix;

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

	public void setTitle(String title) {
		this.title = StringUtils.trimToNull(title);
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
		this.suffix = StringUtils.trimToNull(suffix);
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
			return StringUtils.equals(title, other.title) && 
				StringUtils.equals(lastName, other.lastName) &&
				StringUtils.equals(firstName, other.firstName) &&
				StringUtils.equals(middleName, other.middleName) &&
				StringUtils.equals(suffix, other.suffix);
		}
		return false;
	}
}