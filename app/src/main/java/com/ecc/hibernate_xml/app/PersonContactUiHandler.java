package com.ecc.hibernate_xml.app;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Email;
import com.ecc.hibernate_xml.model.Landline;
import com.ecc.hibernate_xml.model.MobileNumber;
import com.ecc.hibernate_xml.service.ContactService;
import com.ecc.hibernate_xml.util.InputHandler;

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

	private static final String UPDATE_CONTACT_PROMPT = "Please choose a contact to update from the following:\n%s\nEnter Contact ID: ";
	private static final String DELETE_CONTACT_PROMPT = "Please choose a contact to remove from the following:\n%s\nEnter Contact ID: ";
	private static final String CONTACT_DATA_PROMPT = "Please enter the new contact data for \"%s\": ";

	private static ContactService contactService = new ContactService();

	public static Object list(Object parameter) throws Exception {
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
		return 0;
	}

	public static Object addLandline(Object parameter) throws Exception {
		Contact contact = new Landline();
		contact.setData(InputHandler.getNextLineREPL(LANDLINE_PROMPT, ContactService::validateLandline));
		createContact(contact, (Person) parameter);
		return 0;
	}

	public static Object addEmail(Object parameter) throws Exception {
		Contact contact = new Email();
		contact.setData(InputHandler.getNextLineREPL(EMAIL_PROMPT, ContactService::validateEmail));
		createContact(contact, (Person) parameter);
		return 0;
	}

	public static Object addMobileNumber(Object parameter) throws Exception {
		Contact contact = new MobileNumber();
		contact.setData(InputHandler.getNextLineREPL(MOBILE_NUMBER_PROMPT, ContactService::validateMobileNumber));
		createContact(contact, (Person) parameter);
		return 0;
	}

	private static void createContact(Contact contact, Person person) throws Exception {
		contactService.create(contact, person);
		System.out.println(String.format(CREATE_SUCCESS_MESSAGE, contact));
	}

	public static Object update(Object parameter) throws Exception {
		Person person = (Person) parameter;
		List<Contact> contacts = contactService.list(person);

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println(NO_CONTACTS_TO_UPDATE);
		}
		else {		
			String listOfContacts = contacts.stream()
				.map(contact -> contact.toString())
				.collect(Collectors.joining("\n"));

			String userPrompt = String.format(UPDATE_CONTACT_PROMPT, listOfContacts);
			Integer contactId = InputHandler.getNextLine(userPrompt, Integer::valueOf);

			Contact contact = contactService.get(contactId);
			userPrompt = String.format(CONTACT_DATA_PROMPT, contact);
			InputHandler.consumeNextLineREPL(userPrompt, contact::setData);
			contactService.update(contact);

			String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, contact);
			System.out.println(successMessage);
		}

		System.out.println("-------------------");
		return 0;
	}

	public static Object delete(Object parameter) throws Exception {
		Person person = (Person) parameter;
		List<Contact> contacts = contactService.list(person);

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println(NO_CONTACTS_TO_DELETE);
		}
		else {
			String listOfContacts = contacts.stream()
				.map(contact -> contact.toString())
				.collect(Collectors.joining("\n"));

			String userPrompt = String.format(DELETE_CONTACT_PROMPT, listOfContacts);
			Integer contactId = InputHandler.getNextLine(userPrompt, Integer::valueOf);

			contactService.delete(contactId);

			String successMessage = String.format(DELETE_SUCCESS_MESSAGE, contactId);
			System.out.println(successMessage);	
		}

		System.out.println("-------------------");
		return 0;
	}
}