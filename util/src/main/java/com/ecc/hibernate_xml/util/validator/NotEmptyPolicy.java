package com.ecc.hibernate_xml.util.validator;

class NotEmptyPolicy extends AbstractValidatorPolicy {

	String data;
	String errorMessage;

	NotEmptyPolicy(Object data, String errorMessage) {
		this.data = data == null? null: (String) data;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate() throws ValidationException {
		if (data == null || data.trim().isEmpty()) {
			throw new ValidationException(errorMessage);
		}
	}
}
