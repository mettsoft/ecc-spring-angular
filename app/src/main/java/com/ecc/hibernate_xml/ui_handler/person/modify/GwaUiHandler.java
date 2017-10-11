package com.ecc.hibernate_xml.ui_handler.person.modify;

import java.math.BigDecimal;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;

public class GwaUiHandler extends UiHandler {
	private static final String PROMPT = "Please enter the GWA: ";

	private PersonService personService;
	private Person person;

	public GwaUiHandler(String operationName, Person person) {
		super(operationName);
		this.personService = new PersonService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
		BigDecimal GWA = InputHandler.getNextLineREPL(PROMPT, BigDecimal::new);
		person.setGWA(GWA);
		personService.update(person);
		System.out.println(String.format("Successfully updated person's gwa GWA \"%s\"!", GWA));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}