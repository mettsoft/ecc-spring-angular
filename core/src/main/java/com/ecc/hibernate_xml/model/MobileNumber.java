package com.ecc.hibernate_xml.model;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class MobileNumber extends Contact {
	private static final Integer MUST_CHARACTERS = 11;
	
	private MobileNumber() {}

	public MobileNumber(String data) throws ValidationException {
		setData(data);
	}

	@Override
	public String getContactType() {
		return "Mobile Number";
	}

	@Override
	protected ModelValidator configureValidator(ModelValidator validator) throws ValidationException {
		return validator
			.digits("Mobile number must only contain numerical digits.")
			.equalLength(MUST_CHARACTERS, String.format("Mobile number must contain %d digits.", 
				MUST_CHARACTERS));
	}
}