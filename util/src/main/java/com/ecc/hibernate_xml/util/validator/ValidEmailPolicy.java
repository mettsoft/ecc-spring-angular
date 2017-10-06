package com.ecc.hibernate_xml.util.validator;

import org.apache.commons.validator.routines.EmailValidator;

class ValidEmailPolicy extends AbstractValidatorPolicy {

	String data;
	String errorMessage;

	ValidEmailPolicy(Object data, String errorMessage) {
		this.data = data == null? null: (String) data;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (!EmailValidator.getInstance().isValid(data)) {
			throw new ValidationException(errorMessage);
		}
	}
}