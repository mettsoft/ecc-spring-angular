package com.ecc.hibernate_xml.ui_handler.person.update;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Name;

public class NameUiHandler extends UiHandler {

	private static final String TITLE_PROMPT = "Please enter the title: ";
	private static final String LAST_NAME_PROMPT = "Please enter the last name: ";
	private static final String FIRST_NAME_PROMPT = "Please enter the first name: ";
	private static final String MIDDLE_NAME_PROMPT = "Please enter the middle name: ";
	private static final String SUFFIX_PROMPT = "Please enter the suffix: ";

	private PersonService personService;
	private Person person;

	public NameUiHandler(String operationName, Person person) {
		super(operationName);
		this.personService = new PersonService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
		String title = InputHandler.getNextLine(TITLE_PROMPT);
		String lastName = InputHandler.getNextLine(LAST_NAME_PROMPT);
		String firstName = InputHandler.getNextLine(FIRST_NAME_PROMPT);
		String middleName = InputHandler.getNextLine(MIDDLE_NAME_PROMPT);
		String suffix = InputHandler.getNextLine(SUFFIX_PROMPT);

		Name name = new Name(lastName, firstName, middleName);
		name.setTitle(title);
		name.setSuffix(suffix);
		person.setName(name);

		personService.updatePerson(person);
		System.out.println(String.format("Successfully updated person's name to \"%s\"!", name));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}