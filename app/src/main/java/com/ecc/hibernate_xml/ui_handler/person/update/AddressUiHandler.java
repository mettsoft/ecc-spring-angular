package com.ecc.hibernate_xml.ui_handler.person.update;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
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
		String streetNumber = InputHandler.getNextLine(STREET_NUMBER_PROMPT);
		Integer barangay = InputHandler.getNextLine(BARANGAY_PROMPT, Integer::valueOf);
		String municipality = InputHandler.getNextLine(MUNICIPALITY_PROMPT);
		Integer zipCode = InputHandler.getNextLine(ZIP_CODE_PROMPT, Integer::valueOf);

		Address address = new Address(streetNumber, barangay, municipality, zipCode);
		person.setAddress(address);

		personService.updatePerson(person);
		System.out.println(String.format("Successfully updated person's address to \"%s\"!", address));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}