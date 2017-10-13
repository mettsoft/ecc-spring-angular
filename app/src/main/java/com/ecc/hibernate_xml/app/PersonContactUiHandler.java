package com.ecc.hibernate_xml.app;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Email;
import com.ecc.hibernate_xml.model.Landline;
import com.ecc.hibernate_xml.model.MobileNumber;
import com.ecc.hibernate_xml.service.ContactService;
import com.ecc.hibernate_xml.util.app.InputHandler;

public class PersonContactUiHandler {
	private static final String NO_CONTACTS_MESSAGE = "There are no contacts.";
	private static final String NO_CONTACTS_TO_UPDATE = "There are no contacts to update.";
	private static final String NO_CONTACTS_TO_DELETE = "There are no contacts to delete.";
	private static final String LIST_CONTACTS_HEADER = "Person ID [%d] \"%s\" has the following contacts:";
	private static final String CREATE_SUCCESS_MESSAGE = "Successfully created \"%s\"!";
	private static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated \"%s\"!";
	private static final String DELETE_SUCCESS_MESSAGE = "Successfully deleted contact ID \"%d\"!";

	private static final String LANDLINE_PROMPT = "Please enter the landline: ";
	private static final String EMAIL_PROMPT = "Please enter the email: ";
	private static final String MOBILE_NUMBER_PROMPT = "Please enter the mobile number: ";

	private static final String CONTACT_ID_PROMPT = "Enter Contact ID: ";
	private static final String CONTACT_DATA_PROMPT = "Please enter the new contact data for \"%s\": ";

	private final ContactService contactService = new ContactService();

	public void list(Object parameter) throws Exception {
		Person person = (Person) parameter;

		List<Contact> contacts = contactService.list(person);

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println(NO_CONTACTS_MESSAGE);
		}
		else {
			String headerMessage = String.format(LIST_CONTACTS_HEADER, person.getId(), person.getName());
			System.out.println(headerMessage);
			contacts.stream()
				.map(contact -> contact.toString())
				.forEach(System.out::println);			
		}

		System.out.println("-------------------");
	}

	public void create(Object parameter, String contactType) throws Exception {
		switch(contactType) {
			case "Landline":
				saveContact(LANDLINE_PROMPT, new Landline(), (Person) parameter);
				break;
			case "Email":
				saveContact(EMAIL_PROMPT, new Email(), (Person) parameter);
				break;
			case "Mobile Number":
				saveContact(MOBILE_NUMBER_PROMPT, new MobileNumber(), (Person) parameter);
				break;
			default: 
				throw new RuntimeException("No validation rule defined for " + contactType + "!");
		}
	}

	private void saveContact(String prompt, Contact contact, Person person) throws Exception {
		fillContact(prompt, contact);
		contactService.create(contact, person);
		
		String successMessage = String.format(CREATE_SUCCESS_MESSAGE, contact);
		System.out.println(successMessage);
	}

	private void fillContact(String prompt, Contact contact) {
		String data = InputHandler.getNextLineREPL(prompt, arg -> 
			contactService.validateContact(arg, contact.getContactType()));
		contact.setData(data);
	}

	public void update(Object parameter) throws Exception {
		Person person = (Person) parameter;
		List<Contact> contacts = contactService.list(person);

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println(NO_CONTACTS_TO_UPDATE);
		}
		else {		
			Integer contactId = InputHandler.getNextLine(CONTACT_ID_PROMPT, Integer::valueOf);
			Contact contact = contactService.get(contactId);
			String userPrompt = String.format(CONTACT_DATA_PROMPT, contact);
			fillContact(userPrompt, contact);

			contactService.update(contact);

			String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, contact);
			System.out.println(successMessage);
		}
		System.out.println("-------------------");
	}

	public void delete(Object parameter) throws Exception {
		Person person = (Person) parameter;
		List<Contact> contacts = contactService.list(person);
		
		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println(NO_CONTACTS_TO_DELETE);
		}
		else {
			Integer contactId = InputHandler.getNextLine(CONTACT_ID_PROMPT, Integer::valueOf);

			contactService.delete(contactId);

			String successMessage = String.format(DELETE_SUCCESS_MESSAGE, contactId);
			System.out.println(successMessage);	
		}

		System.out.println("-------------------");
	}
}