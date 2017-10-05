package com.ecc.hibernate_xml.ui_handler.person.modify;

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

		Name name = person.getName();

		InputHandler.getNextLineREPL(TITLE_PROMPT, input -> {
			name.setTitle(input);
			return 0;
		});
		InputHandler.getNextLineREPL(LAST_NAME_PROMPT, input -> {
			name.setLastName(input);
			return 0;
		});
		InputHandler.getNextLineREPL(FIRST_NAME_PROMPT, input -> {
			name.setFirstName(input);
			return 0;
		});
		InputHandler.getNextLineREPL(MIDDLE_NAME_PROMPT, input -> {
			name.setMiddleName(input);
			return 0;
		});
		InputHandler.getNextLineREPL(SUFFIX_PROMPT, input -> {
			name.setSuffix(input);
			return 0;
		});

		person.setName(name);

		personService.updatePerson(person);
		System.out.println(String.format("Successfully updated person's name to \"%s\"!", name));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}