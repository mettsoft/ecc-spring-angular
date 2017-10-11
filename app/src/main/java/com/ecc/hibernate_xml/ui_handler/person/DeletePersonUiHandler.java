package com.ecc.hibernate_xml.ui_handler.person;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.app.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;

public class DeletePersonUiHandler extends UiHandler {
	private static final String PROMPT = "Please enter the person ID you wish to delete: ";

	private PersonService personService;

	public DeletePersonUiHandler(String operationName) {
		super(operationName);
		personService = new PersonService();
	}

	@Override 
	public void onHandle() throws Exception {
		Integer personId = InputHandler.getNextLine(PROMPT, Integer::valueOf);
		personService.delete(personId);
		System.out.println(String.format("Successfully deleted the person ID \"%d\"!", personId));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}