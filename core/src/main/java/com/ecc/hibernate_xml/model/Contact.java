package com.ecc.hibernate_xml.model;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public abstract class Contact {
	private Integer id;
	private String data;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(String data) throws ValidationException {
		ModelValidator validator = ModelValidator.create(data)
			.notEmpty(String.format("%s cannot be empty.", getContactType()));
	
		configureValidator(validator).validate();	
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public String getData() {
		return data;
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

	protected abstract ModelValidator configureValidator(ModelValidator validator) throws ValidationException;
}