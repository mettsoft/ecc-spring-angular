package com.ecc.hibernate_xml.model;

import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class MobileNumber extends Contact {
	@Override
	public String getContactType() {
		return "Mobile Number";
	}
}