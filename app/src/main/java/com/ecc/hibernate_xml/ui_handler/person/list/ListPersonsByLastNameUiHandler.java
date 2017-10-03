package com.ecc.hibernate_xml.ui_handler.person.list;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.service.PersonService;

public class ListPersonsByLastNameUiHandler extends UiHandler {

	private PersonService personService;

	public ListPersonsByLastNameUiHandler(String operationName) {
		super(operationName);
		personService = new PersonService();
	}

	@Override 
	public void onHandle() throws Exception {
		personService.listPersonsByLastName().stream().forEach(System.out::println);
		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}