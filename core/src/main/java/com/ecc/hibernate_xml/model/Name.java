package com.ecc.hibernate_xml.model;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Name {
	private String title;
	private String lastName;
	private String firstName;
	private String middleName;
	private String suffix;

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

	@Override
	public String toString() {
		List<String> tokens = new ArrayList<>(5);

		if (title != null) {
			tokens.add(title);
		}

		tokens.add(firstName);
		tokens.add(middleName);
		tokens.add(lastName);

		if (suffix != null) {
			tokens.add(suffix);
		}

		return tokens.stream().collect(Collectors.joining(" "));
	}
}