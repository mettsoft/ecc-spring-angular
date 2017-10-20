package com.ecc.hibernate_xml.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class NameDTO {
	private String title;
	private String lastName;
	private String firstName;
	private String middleName;
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
		this.title = title;
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
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		List<String> tokens = new ArrayList<>(5);

		if (title != null) {
			tokens.add(title);
		}

		tokens.add(lastName + ",");
		tokens.add(firstName);
		tokens.add(middleName);

		if (suffix != null) {
			tokens.add(suffix);
		}

		return tokens.stream().collect(Collectors.joining(" "));
	}
}