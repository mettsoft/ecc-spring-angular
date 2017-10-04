package com.ecc.hibernate_xml.model;

public class Landline extends Contact {
	private static final Integer MUST_CHARACTERS = 7;

	private Landline() {

	}

	public Landline(String data) throws ModelException {
		setData(data);
	}

	@Override
	protected void onValidate(String data) throws ModelException {
		try {
			Integer.parseInt(data);
		}
		catch(NumberFormatException exception) {
			throw new ModelException("Landline must only contain numerical digits.");
		}

		if (data.length() != MUST_CHARACTERS) {
			throw new ModelException(String.format("Landline must contain %d digits.", 
				MUST_CHARACTERS));
		}
	}
}