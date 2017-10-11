package com.ecc.hibernate_xml.ui_handler.person.modify;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Address;

public class AddressUiHandler extends UiHandler {

	private static final String STREET_NUMBER_PROMPT = "Please enter the street number: ";
	private static final String BARANGAY_PROMPT = "Please enter the barangay: ";
	private static final String MUNICIPALITY_PROMPT = "Please enter the municipality: ";
	private static final String ZIP_CODE_PROMPT = "Please enter the zip code: ";

	private PersonService personService;
	private Person person;

	public AddressUiHandler(String operationName, Person person) {
		super(operationName);
		this.personService = new PersonService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
		Address.Factory addressFactory = new Address.Factory();
		InputHandler.getNextLineREPL(STREET_NUMBER_PROMPT, addressFactory::setStreetNumber);
		addressFactory.setBarangay(InputHandler.getNextLineREPL(BARANGAY_PROMPT, Integer::valueOf));
		InputHandler.getNextLineREPL(MUNICIPALITY_PROMPT, addressFactory::setMunicipality);
		addressFactory.setZipCode(InputHandler.getNextLineREPL(ZIP_CODE_PROMPT, Integer::valueOf));

		Address address = addressFactory.build();
		person.setAddress(address);

		personService.update(person);
		System.out.println(String.format("Successfully updated person's address to \"%s\"!", address));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}