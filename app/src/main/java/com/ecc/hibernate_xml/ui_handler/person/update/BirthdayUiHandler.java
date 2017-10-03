package com.ecc.hibernate_xml.ui_handler.person.update;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;

public class BirthdayUiHandler extends UiHandler {

	private static final String PROMPT = "Please enter the birthday (yyyy-MM-dd): ";

	private PersonService personService;
	private Person person;

	public BirthdayUiHandler(String operationName, Person person) {
		super(operationName);
		this.personService = new PersonService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = InputHandler.getNextLine(PROMPT, dateFormat::parse);
		person.setBirthday(birthday);

		personService.updatePerson(person);
		System.out.println(String.format("Successfully updated person's birthday to \"%s\"!", birthday));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}