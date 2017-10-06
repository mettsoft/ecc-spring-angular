package com.ecc.hibernate_xml.model;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class Email extends Contact {
	private static final Integer MAX_CHARACTERS = 50;

	private Email() {}

	public Email(String data) throws ValidationException {
		setData(data);
	}

	@Override
	protected ModelValidator configureValidator(ModelValidator validator) throws ValidationException {
		return validator
			.maxLength(MAX_CHARACTERS, String.format("Email must not exceed %d characters.", 
				MAX_CHARACTERS))
			.validEmail("Email is invalid.");
	}
}