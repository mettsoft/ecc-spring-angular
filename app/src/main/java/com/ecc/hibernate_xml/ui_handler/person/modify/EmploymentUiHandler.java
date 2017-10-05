package com.ecc.hibernate_xml.ui_handler.person.modify;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;

public class EmploymentUiHandler extends UiHandler {

	private static final String EMPLOYMENT_PROMPT = "Please enter the employment status (y/n): ";
	private static final String DATE_PROMPT = "Please enter date hired (yyyy-MM-dd): ";

	private PersonService personService;
	private Person person;

	public EmploymentUiHandler(String operationName, Person person) {
		super(operationName);
		this.personService = new PersonService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
		Boolean currentlyEmployed = InputHandler.getNextLine(EMPLOYMENT_PROMPT, input -> {
			if (input.compareTo("y") == 0) {
				return true;
			}
			else if (input.compareTo("n") == 0) {
				return false;
			}
			else {
				throw new ParseException("Invalid input!", 0);
			}
		});

		person.setCurrentlyEmployed(currentlyEmployed);

		if (currentlyEmployed) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateHired = InputHandler.getNextLine(DATE_PROMPT, dateFormat::parse);
			person.setDateHired(dateHired);			
		}

		personService.updatePerson(person);
		System.out.println("Successfully updated person's employment status!");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}