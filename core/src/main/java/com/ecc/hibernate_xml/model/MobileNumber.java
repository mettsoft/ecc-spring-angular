package com.ecc.hibernate_xml.model;

public class MobileNumber extends Contact {
	private static final Integer MUST_CHARACTERS = 11;
	
	private MobileNumber() {

	}

	public MobileNumber(String data) throws ModelException {
		setData(data);
	}

	@Override
	public String getContactType() {
		return "Mobile Number";
	}

	@Override
	protected void onValidate(String data) throws ModelException {
		try {
			Long.parseLong(data);
		}
		catch(NumberFormatException exception) {
			throw new ModelException("Mobile number must only contain numerical digits.");
		}

		if (data.length() != MUST_CHARACTERS) {
			throw new ModelException(String.format("Mobile number must contain %d digits.", 
				MUST_CHARACTERS));
		}
	}
}