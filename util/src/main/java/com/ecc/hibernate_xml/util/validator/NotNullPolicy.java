package com.ecc.hibernate_xml.util.validator;

class NotNullPolicy extends AbstractValidatorPolicy {

	Object data;
	String errorMessage;

	NotNullPolicy(Object data, String errorMessage) {
		this.data = data;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (data == null) {
			throw new ValidationException(errorMessage);
		}
	}
}
