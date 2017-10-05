package com.ecc.hibernate_xml.ui_handler.person;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.NameUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.AddressUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.BirthdayUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.GwaUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.EmploymentUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.contact.ListContactsUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.contact.AddContactUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.contact.EditContactUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.contact.DeleteContactUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.role.ListRolesUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.role.AddRoleUiHandler;
import com.ecc.hibernate_xml.ui_handler.person.modify.role.RemoveRoleUiHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.InputHandler;

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
		new CompositeUiHandler()
			.add(new NameUiHandler("Change Name.", person))
			.add(new AddressUiHandler("Change Address.", person))
			.add(new BirthdayUiHandler("Change Birthday.", person))
			.add(new GwaUiHandler("Change GWA", person))
			.add(new EmploymentUiHandler("Change Employment Status.", person))
			.add(new CompositeUiHandler("Manage Contact Information.")
				.add(new ListContactsUiHandler("List Contact Information.", person))
				.add(new AddContactUiHandler("Add Contact Information.", person))
				.add(new EditContactUiHandler("Edit Contact Information.", person))
				.add(new DeleteContactUiHandler("Delete Contact Information", person)))
			.add(new CompositeUiHandler("Manage Roles.")
				.add(new ListRolesUiHandler("List Roles.", person))
				.add(new AddRoleUiHandler("Add Role.", person))
				.add(new RemoveRoleUiHandler("Remove Role.", person)))
			.handle();
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}