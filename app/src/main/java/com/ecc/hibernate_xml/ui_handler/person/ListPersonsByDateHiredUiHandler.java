package com.ecc.hibernate_xml.ui_handler.person;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.service.PersonService;

public class ListPersonsByDateHiredUiHandler extends UiHandler {

	private PersonService personService;

	public ListPersonsByDateHiredUiHandler(String operationName) {
		super(operationName);
		personService = new PersonService();
	}

	@Override 
	public void onHandle() throws Exception {
		personService.listPersonsByDateHired().stream().forEach(System.out::println);
		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}