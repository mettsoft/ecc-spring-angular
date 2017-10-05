package com.ecc.hibernate_xml.ui_handler.person.modify.contact;

import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Map;
import java.util.List;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.ContactService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Contact;

public class DeleteContactUiHandler extends UiHandler {

	private static final String PROMPT = "Please choose a contact to remove from the following: ";

	private ContactService contactService;
	private Person person;

	public DeleteContactUiHandler(String operationName, Person person) {
		super(operationName);
		this.contactService = new ContactService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {

		List<Contact> contacts = contactService.listContacts(person);

		Map<Integer, Contact> mapOfContacts = contacts.stream()
			.collect(Collectors.toMap(contact -> contact.getId(), Function.identity()));

		String listOfContacts = contacts.stream()
			.map(contact -> contact.toString())
			.collect(Collectors.joining("\n"));

		Integer contactId = InputHandler.getNextLine(
			String.format("%s\n%s\nContact ID: ", PROMPT, listOfContacts), Integer::valueOf);

		Contact contact = mapOfContacts.get(contactId);
		contactService.deleteContact(contact);
		System.out.println(String.format(
			"Successfully removed \"%s\"  from Person \"%s\"!", contact, person.getName()));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}