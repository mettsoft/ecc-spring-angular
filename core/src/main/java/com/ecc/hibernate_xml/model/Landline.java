package com.ecc.hibernate_xml.model;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class Landline extends Contact {
	private static final Integer MUST_CHARACTERS = 7;

	private Landline() {}

	public Landline(String data) throws ValidationException {
		setData(data);
	}

	@Override
	protected ModelValidator configureValidator(ModelValidator validator) throws ValidationException {
		return validator
			.digits("Landline must only contain numerical digits.")
			.equalLength(MUST_CHARACTERS, String.format("Landline must contain %d digits.", 
				MUST_CHARACTERS));
	}
}