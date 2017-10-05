package com.ecc.hibernate_xml.ui_handler.person.list;

import java.util.List;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;

public class ListPersonsByDateHiredUiHandler extends UiHandler {

	private PersonService personService;

	public ListPersonsByDateHiredUiHandler(String operationName) {
		super(operationName);
		personService = new PersonService();
	}

	@Override 
	public void onHandle() throws Exception {
		System.out.println("-------------------");

		List<Person> persons = personService.listPersonsByDateHired();
		if (persons.isEmpty()) {
			System.out.println("There are no persons.");
		}
		else {	
			persons.stream().forEach(System.out::println);
		}
		
		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}