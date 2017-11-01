package com.ecc.spring_xml.util.validator;

abstract class ValidatorPolicy {
	private String messageTemplate = "Default Error Message!";

	void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	void validate(Object data, Object... arguments) throws ValidationException {
		if (!onValidate(data, arguments)) {
			throw new ValidationException(String.format(messageTemplate, arguments));
		}
	}

	abstract Boolean onValidate(Object data, Object... arguments);
}