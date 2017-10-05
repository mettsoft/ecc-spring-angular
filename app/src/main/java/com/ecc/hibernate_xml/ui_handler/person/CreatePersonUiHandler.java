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
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Name;

public class CreatePersonUiHandler extends UiHandler {
	private static final String LAST_NAME_PROMPT = "Please enter the last name: ";
	private static final String FIRST_NAME_PROMPT = "Please enter the first name: ";
	private static final String MIDDLE_NAME_PROMPT = "Please enter the middle name: ";

	private PersonService personService;

	public CreatePersonUiHandler(String operationName) {
		super(operationName);
		personService = new PersonService();
	}

	@Override 
	public void onHandle() throws Exception {
		Name.Factory nameFactory = new Name.Factory();
		InputHandler.getNextLineREPL(LAST_NAME_PROMPT, nameFactory::setLastName);
		InputHandler.getNextLineREPL(FIRST_NAME_PROMPT, nameFactory::setFirstName);
		InputHandler.getNextLineREPL(MIDDLE_NAME_PROMPT, nameFactory::setMiddleName);
		Name name = nameFactory.build();

		Person person = new Person(name);
		personService.createPerson(person);

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

		System.out.println(String.format("Successfully created the person \"%s\" with ID \"%d\"!", 
			person.getName(), person.getId()));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}