package com.ecc.hibernate_xml.ui_handler.person.modify.contact;

import java.util.stream.Collectors;
import java.util.List;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.app.InputHandler;
import com.ecc.hibernate_xml.service.ContactService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Contact;

public class EditContactUiHandler extends UiHandler {

	private static final String SELECT_CONTACT_PROMPT = "Please choose a contact to edit from the following: ";
	private static final String CONTACT_DATA_PROMPT = "Please enter the new contact data for \"%s\": ";

	private ContactService contactService;
	private Person person;

	public EditContactUiHandler(String operationName, Person person) {
		super(operationName);
		this.contactService = new ContactService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
		List<Contact> contacts = contactService.list(person);

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println("There are no contacts to delete.");
		}
		else {		
			String listOfContacts = contacts.stream()
				.map(contact -> contact.toString())
				.collect(Collectors.joining("\n"));

			Integer contactId = InputHandler.getNextLine(
				String.format("%s\n%s\n Enter Contact ID: ", SELECT_CONTACT_PROMPT, listOfContacts), Integer::valueOf);

			Contact contact = contactService.get(contactId);
			contact.setData(InputHandler.getNextLine(String.format(CONTACT_DATA_PROMPT, contact)));
			contactService.update(contact);
			System.out.println(String.format(
				"Successfully updated \"%s\"  of Person ID [%d] \"%s\"!", contact, 
				person.getId(), person.getName()));
		}
		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}