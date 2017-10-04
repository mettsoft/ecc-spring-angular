package com.ecc.hibernate_xml.model;

import org.apache.commons.validator.routines.EmailValidator;

public class Email extends Contact {
	private static final Integer MAX_CHARACTERS = 50;

	private Email() {

	}

	public Email(String data) throws ModelException {
		setData(data);
	}

	@Override
	protected void onValidate(String data) throws ModelException {
		if (data.length() > MAX_CHARACTERS) {
			throw new ModelException(String.format("Email must not exceed %d characters.", 
				MAX_CHARACTERS));
		}
		else if (!EmailValidator.getInstance().isValid(data)) {
			throw new ModelException("Email is invalid.");
		}
	}
}