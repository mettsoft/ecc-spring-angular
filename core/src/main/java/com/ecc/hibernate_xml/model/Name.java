package com.ecc.hibernate_xml.model;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Name {
	public static class Factory {
		private Name name;

		public Factory() {
			name = new Name();
		}

		public Factory setLastName(String lastName) throws ModelException {
			name.setLastName(lastName);
			return this;
		}

		public Factory setMiddleName(String middleName) throws ModelException {
			name.setMiddleName(middleName);
			return this;
		}

		public Factory setFirstName(String firstName) throws ModelException {
			name.setFirstName(firstName);
			return this;
		}

		public Name build() throws ModelException {
			name.setLastName(name.lastName);
			name.setMiddleName(name.middleName);
			name.setFirstName(name.firstName);
			return name;
		}
	}

	private static Integer MAX_CHARACTERS = 20;

	private String title;
	private String lastName;
	private String firstName;
	private String middleName;
	private String suffix;
		
	private Name() {

	}

	public Name(String lastName, String firstName, String middleName) throws ModelException {
		setLastName(lastName);
		setFirstName(firstName);
		setMiddleName(middleName);
	}

	public void setTitle(String title) throws ModelException  {
		if (title != null && title.length() > MAX_CHARACTERS) {
			throw new ModelException(String.format("Title must not exceed %d characters.", 
				MAX_CHARACTERS));
		}
		this.title = title;
	}

	public void setLastName(String lastName) throws ModelException {
		if (lastName == null || lastName.trim().isEmpty()) {
			throw new ModelException("Last name cannot be empty.");
		}
		else if (lastName.length() > MAX_CHARACTERS) {
			throw new ModelException(String.format("Last name must not exceed %d characters.", 
				MAX_CHARACTERS));
		}
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) throws ModelException  {
		if (firstName == null || firstName.trim().isEmpty()) {
			throw new ModelException("First name cannot be empty.");
		}
		else if (firstName.length() > MAX_CHARACTERS) {
			throw new ModelException(String.format("First name must not exceed %d characters.", 
				MAX_CHARACTERS));
		}
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) throws ModelException  {
		if (middleName == null || middleName.trim().isEmpty()) {
			throw new ModelException("Middle name cannot be empty.");
		}
		else if (middleName.length() > MAX_CHARACTERS) {
			throw new ModelException(String.format("Middle name must not exceed %d characters.", 
				MAX_CHARACTERS));
		}
		this.middleName = middleName;
	}

	public void setSuffix(String suffix) throws ModelException  {
		if (suffix != null && suffix.length() > MAX_CHARACTERS) {
			throw new ModelException(String.format("Suffix must not exceed %d characters.", 
				MAX_CHARACTERS));
		}
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