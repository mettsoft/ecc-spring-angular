package com.ecc.hibernate_xml.model;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class Name {
	public static class Factory {
		private Name name;

		public Factory() {
			name = new Name();
		}

		public Factory setLastName(String lastName) throws ValidationException {
			name.setLastName(lastName);
			return this;
		}

		public Factory setMiddleName(String middleName) throws ValidationException {
			name.setMiddleName(middleName);
			return this;
		}

		public Factory setFirstName(String firstName) throws ValidationException {
			name.setFirstName(firstName);
			return this;
		}

		public Name build() throws ValidationException {
			name.setLastName(name.lastName);
			name.setMiddleName(name.middleName);
			name.setFirstName(name.firstName);
			return name;
		}
	}

	private static final Integer MAX_CHARACTERS = 20;
	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed " + MAX_CHARACTERS + " characters.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";

	private String title;
	private String lastName;
	private String firstName;
	private String middleName;
	private String suffix;
		
	private Name() {}

	public Name(String lastName, String firstName, String middleName) throws ValidationException {
		setLastName(lastName);
		setFirstName(firstName);
		setMiddleName(middleName);
	}

	public void setTitle(String title) throws ValidationException  {
		ModelValidator
			.create(title)
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Title"))
			.validate();

		this.title = title != null && title.trim().isEmpty()? null: title;
	}

	public void setLastName(String lastName) throws ValidationException {
		ModelValidator
			.create(lastName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Last name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Last name"))
			.validate();

		this.lastName = lastName;
	}

	public void setFirstName(String firstName) throws ValidationException  {
		ModelValidator
			.create(firstName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "First name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"First name"))
			.validate();

		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) throws ValidationException  {
		ModelValidator
			.create(middleName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Middle name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Middle name"))
			.validate();

		this.middleName = middleName;
	}

	public void setSuffix(String suffix) throws ValidationException  {
		ModelValidator
			.create(suffix)
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Suffix"))
			.validate();

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