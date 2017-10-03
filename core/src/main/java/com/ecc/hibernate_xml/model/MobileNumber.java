package com.ecc.hibernate_xml.model;

public class MobileNumber extends Contact {
	public MobileNumber() {

	}

	public MobileNumber(String data) {
		setData(data);
	}

	@Override
	public String getContactType() {
		return "Mobile Number";
	}
}