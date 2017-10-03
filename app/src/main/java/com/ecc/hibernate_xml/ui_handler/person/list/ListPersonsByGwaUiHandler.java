package com.ecc.hibernate_xml.ui_handler.person.list;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.service.PersonService;

public class ListPersonsByGwaUiHandler extends UiHandler {

	private PersonService personService;

	public ListPersonsByGwaUiHandler(String operationName) {
		super(operationName);
		personService = new PersonService();
	}

	@Override 
	public void onHandle() throws Exception {
		personService.listPersonsByGwa().stream().forEach(System.out::println);
		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}