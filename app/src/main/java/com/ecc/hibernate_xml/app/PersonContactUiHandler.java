package com.ecc.hibernate_xml.app;

import java.util.List;

import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.dto.ContactDTO;
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
		PersonDTO person = (PersonDTO) parameter;
		List<ContactDTO> contacts = contactService.list(person);

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
		PersonDTO person = (PersonDTO) parameter;

		ContactDTO contact = new ContactDTO();
		contact.setPerson(person);
		contact.setContactType(contactType);

		String prompt = "";
		switch(contactType) {
			case "Landline":
				prompt = LANDLINE_PROMPT;
				break;
			case "Email":
				prompt = EMAIL_PROMPT;
				break;
			case "Mobile Number":
				prompt = MOBILE_NUMBER_PROMPT;
				break;
			default: 
				throw new RuntimeException("No validation rule defined for " + contactType + "!");
		}

		fillContact(prompt, contact);
		contact.setId((Integer) contactService.create(contact));
		
		String successMessage = String.format(CREATE_SUCCESS_MESSAGE, contact);
		System.out.println(successMessage);
	}

	private void fillContact(String prompt, ContactDTO contact) {
		String data = InputHandler.getNextLineREPL(prompt, arg -> 
			contactService.validateContact(arg, contact.getContactType()));
		contact.setData(data);
	}

	public void update(Object parameter) throws Exception {
		PersonDTO person = (PersonDTO) parameter;
		List<ContactDTO> contacts = contactService.list(person);

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println(NO_CONTACTS_TO_UPDATE);
		}
		else {		
			Integer contactId = InputHandler.getNextLine(CONTACT_ID_PROMPT, Integer::valueOf);
			ContactDTO contact = contactService.get(contactId, person);
			String userPrompt = String.format(CONTACT_DATA_PROMPT, contact);
			fillContact(userPrompt, contact);

			contactService.update(contact);

			String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, contact);
			System.out.println(successMessage);
		}
		System.out.println("-------------------");
	}

	public void delete(Object parameter) throws Exception {
		PersonDTO person = (PersonDTO) parameter;
		List<ContactDTO> contacts = contactService.list(person);
		
		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println(NO_CONTACTS_TO_DELETE);
		}
		else {
			Integer contactId = InputHandler.getNextLine(CONTACT_ID_PROMPT, Integer::valueOf);

			contactService.delete(contactId, person);

			String successMessage = String.format(DELETE_SUCCESS_MESSAGE, contactId);
			System.out.println(successMessage);	
		}

		System.out.println("-------------------");
	}
}