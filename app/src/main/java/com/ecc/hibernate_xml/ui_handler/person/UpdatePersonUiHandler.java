package com.ecc.hibernate_xml.ui_handler.person;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.NameUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.AddressUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.BirthdayUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.GwaUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.EmploymentUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.role.ListRolesUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.role.AddRoleUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.update.role.RemoveRoleUiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Name;

public class UpdatePersonUiHandler extends UiHandler {

	private static final String PROMPT = "Please enter the person ID you wish to update: ";

	private PersonService personService;

	public UpdatePersonUiHandler(String operationName) {
		super(operationName);
		personService = new PersonService();
	}

	@Override 
	public void onHandle() throws Exception {

		Integer personId = InputHandler.getNextLine(PROMPT, Integer::valueOf);
		Person person = personService.getPerson(personId);
		// TODO: Manage Contacts
		new CompositeUiHandler()
			.add(new NameUiHandler("Change name.", person))
			.add(new AddressUiHandler("Change address.", person))
			.add(new BirthdayUiHandler("Change birthday.", person))
			.add(new GwaUiHandler("Change GWA", person))
			.add(new EmploymentUiHandler("Change employment status.", person))
			.add(new CompositeUiHandler("Manage Roles")
				.add(new ListRolesUiHandler("List Roles", person))
				.add(new AddRoleUiHandler("Add Role", person))
				.add(new RemoveRoleUiHandler("Remove Role", person)))
			.handle();
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}