package com.ecc.hibernate_xml.ui_handler.person.modify.contact;

import java.util.stream.Collectors;
import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputException;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Landline;
import com.ecc.hibernate_xml.model.Email;
import com.ecc.hibernate_xml.model.MobileNumber;

public class AddContactUiHandler extends UiHandler {

	private static final String CONTACT_TYPE_PROMPT = "Please choose contact from the following:\n1. Landline\n2. Email\n3. Mobile Number\nChoose: ";
	private static final String LANDLINE_PROMPT = "Please enter the landline: ";
	private static final String EMAIL_PROMPT = "Please enter the email: ";
	private static final String MOBILE_NUMBER_PROMPT = "Please enter the mobile number: ";
	private PersonService personService;
	private Person person;

	public AddContactUiHandler(String operationName, Person person) {
		super(operationName);
		this.personService = new PersonService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {

		Integer contactType = InputHandler.getNextLine(CONTACT_TYPE_PROMPT, Integer::valueOf);
		Contact contact = null;

		switch (contactType) {
			case 1: 
				contact = new Landline(InputHandler.getNextLine(LANDLINE_PROMPT));
				break;
			case 2: 
				contact = new Email(InputHandler.getNextLine(EMAIL_PROMPT));
				break;
			case 3: 
				contact = new MobileNumber(InputHandler.getNextLine(MOBILE_NUMBER_PROMPT));
				break;
			default:
				throw new InputException(null);
		}		

		personService.addContactToPerson(contact, person);
		System.out.println(String.format(
			"Successfully added \"%s\"  to Person \"%s\"!", contact, person.getName()));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}