package com.ecc.hibernate_xml.model;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class Role {
	private static final Integer MAX_CHARACTERS = 20;
	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed " + MAX_CHARACTERS + " characters.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";

	private Integer id;
	private String name;

	private Role() {}

	public Role(String name) throws ModelException {
		setName(name);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) throws ModelException {
		ModelValidator
			.create(name)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Role name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Role name"))
			.validate();

		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Role) {
			Role otherRole = (Role) object; 
			return id.equals(otherRole.id) && name.equals(otherRole.name);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("[ID=%d] %s", id, name);
	}
}