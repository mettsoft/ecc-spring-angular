package com.ecc.hibernate_xml.util.validator;

interface ValidatorPolicy {
	void validate() throws ValidationException;
}