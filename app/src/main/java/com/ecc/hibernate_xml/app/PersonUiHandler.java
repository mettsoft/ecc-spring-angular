package com.ecc.hibernate_xml.app;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Name;
import com.ecc.hibernate_xml.model.Address;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Email;
import com.ecc.hibernate_xml.model.Landline;
import com.ecc.hibernate_xml.model.MobileNumber;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.service.ContactService;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.util.InputException;

public class PersonUiHandler {
	private static final String UPDATE_PROMPT = "Please enter the person ID you wish to update: ";
	private static final String DELETE_PROMPT = "Please enter the person ID you wish to delete: ";

	private static final String TITLE_PROMPT = "Please enter the title: ";
	private static final String LAST_NAME_PROMPT = "Please enter the last name: ";
	private static final String FIRST_NAME_PROMPT = "Please enter the first name: ";
	private static final String MIDDLE_NAME_PROMPT = "Please enter the middle name: ";
	private static final String SUFFIX_PROMPT = "Please enter the suffix: ";

	private static final String STREET_NUMBER_PROMPT = "Please enter the street number: ";
	private static final String BARANGAY_PROMPT = "Please enter the barangay: ";
	private static final String MUNICIPALITY_PROMPT = "Please enter the municipality: ";
	private static final String ZIP_CODE_PROMPT = "Please enter the zip code: ";

	private static final String BIRTHDAY_PROMPT = "Please enter the birthday (yyyy-MM-dd): ";
	private static final String GWA_PROMPT = "Please enter the GWA: ";

	private static final String EMPLOYMENT_PROMPT = "Please enter the employment status (y/n): ";
	private static final String DATE_PROMPT = "Please enter date hired (yyyy-MM-dd): ";

	private static final String CONTACT_TYPE_PROMPT = "Please choose contact from the following:\n1. Landline\n2. Email\n3. Mobile Number\nChoose: ";
	private static final String LANDLINE_PROMPT = "Please enter the landline: ";
	private static final String EMAIL_PROMPT = "Please enter the email: ";
	private static final String MOBILE_NUMBER_PROMPT = "Please enter the mobile number: ";

	private static final String SELECT_CONTACT_PROMPT = "Please choose a contact to edit from the following: ";
	private static final String CONTACT_DATA_PROMPT = "Please enter the new contact data for \"%s\": ";

	private static final String DELETE_CONTACT_PROMPT = "Please choose a contact to remove from the following: ";

	private static final String ADD_ROLE_PROMPT = "Please choose role from the following: ";
	private static final String REMOVE_ROLE_PROMPT = "Please choose role to remove from the following: ";

	private static PersonService personService = new PersonService();
	private static RoleService roleService = new RoleService();
	private static ContactService contactService = new ContactService();

	public static Object listByDateHired(Object parameter) {		
		System.out.println("-------------------");

		List<Person> persons = personService.listPersonsByDateHired();
		if (persons.isEmpty()) {
			System.out.println("There are no persons.");
		}
		else {	
			persons.stream().forEach(System.out::println);
		}
		
		System.out.println("-------------------");
		return 0;
	}

	public static Object listByLastName(Object parameter) {		
		System.out.println("-------------------");

		List<Person> persons = personService.listPersonsByLastName();
		if (persons.isEmpty()) {
			System.out.println("There are no persons.");
		}
		else {	
			persons.stream().forEach(System.out::println);
		}
		
		System.out.println("-------------------");
		return 0;
	}

	public static Object listByGWA(Object parameter) {		
		System.out.println("-------------------");

		List<Person> persons = personService.listPersonsByGwa();
		if (persons.isEmpty()) {
			System.out.println("There are no persons.");
		}
		else {	
			persons.stream().forEach(System.out::println);
		}

		System.out.println("-------------------");
		return 0;
	}

	public static Object create(Object parameter) throws Exception {	
		Name.Factory nameFactory = new Name.Factory();
		InputHandler.getNextLineREPL(LAST_NAME_PROMPT, nameFactory::setLastName);
		InputHandler.getNextLineREPL(FIRST_NAME_PROMPT, nameFactory::setFirstName);
		InputHandler.getNextLineREPL(MIDDLE_NAME_PROMPT, nameFactory::setMiddleName);
		Name name = nameFactory.build();

		Person person = new Person(name);
		personService.create(person);

		System.out.println(String.format("Successfully created the person \"%s\" with ID \"%d\"!", 
			person.getName(), person.getId()));	

		return person;
	}

	public static Object choose(Object parameter) throws Exception {
		Integer personId = InputHandler.getNextLine(UPDATE_PROMPT, Integer::valueOf);
		return personService.get(personId);	
	}

	public static Object changeName(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Name name = person.getName();

		InputHandler.getNextLineREPL(TITLE_PROMPT, input -> {
			name.setTitle(input);
			return 0;
		});
		InputHandler.getNextLineREPL(LAST_NAME_PROMPT, input -> {
			name.setLastName(input);
			return 0;
		});
		InputHandler.getNextLineREPL(FIRST_NAME_PROMPT, input -> {
			name.setFirstName(input);
			return 0;
		});
		InputHandler.getNextLineREPL(MIDDLE_NAME_PROMPT, input -> {
			name.setMiddleName(input);
			return 0;
		});
		InputHandler.getNextLineREPL(SUFFIX_PROMPT, input -> {
			name.setSuffix(input);
			return 0;
		});

		person.setName(name);

		personService.update(person);
		System.out.println(String.format("Successfully updated person's name to \"%s\"!", name));
		return 0;
	}

	public static Object changeAddress(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Address.Factory addressFactory = new Address.Factory();
		InputHandler.getNextLineREPL(STREET_NUMBER_PROMPT, addressFactory::setStreetNumber);
		addressFactory.setBarangay(InputHandler.getNextLineREPL(BARANGAY_PROMPT, Integer::valueOf));
		InputHandler.getNextLineREPL(MUNICIPALITY_PROMPT, addressFactory::setMunicipality);
		addressFactory.setZipCode(InputHandler.getNextLineREPL(ZIP_CODE_PROMPT, Integer::valueOf));

		Address address = addressFactory.build();
		person.setAddress(address);

		personService.update(person);
		System.out.println(String.format("Successfully updated person's address to \"%s\"!", address));
		return 0;
	}

	public static Object changeBirthday(Object parameter) throws Exception {
		Person person = (Person) parameter;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = InputHandler.getNextLineREPL(BIRTHDAY_PROMPT, dateFormat::parse);
		person.setBirthday(birthday);

		personService.update(person);
		System.out.println(String.format(
			"Successfully updated person's birthday to \"%s\"!", dateFormat.format(birthday)));
		return 0;
	}

	public static Object changeGWA(Object parameter) throws Exception {
		Person person = (Person) parameter;
		BigDecimal GWA = InputHandler.getNextLineREPL(GWA_PROMPT, BigDecimal::new);
		person.setGWA(GWA);
		personService.update(person);
		System.out.println(String.format("Successfully updated person's gwa GWA \"%s\"!", GWA));
		return 0;
	}

	public static Object changeEmploymentStatus(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Boolean currentlyEmployed = InputHandler.getNextLineREPL(EMPLOYMENT_PROMPT, input -> {
			if (input.equals("y")) {
				return true;
			}
			else if (input.equals("n")) {
				return false;
			}
			throw new ParseException("Invalid input!", 0);
		});

		person.setCurrentlyEmployed(currentlyEmployed);

		if (currentlyEmployed) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateHired = InputHandler.getNextLineREPL(DATE_PROMPT, dateFormat::parse);
			person.setDateHired(dateHired);			
		}

		personService.update(person);
		System.out.println("Successfully updated person's employment status!");
		return 0;
	}

	public static Object listContacts(Object parameter) throws Exception {
		Person person = (Person) parameter;

		List<Contact> contacts = contactService.list(person);

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println("There are no contacts.");
		}
		else {
			System.out.println(String.format("Person \"%s\" has the following contacts:", 
				person.getName()));
			contacts.stream()
				.map(contact -> contact.toString())
				.forEach(System.out::println);			
		}
		System.out.println("-------------------");

		return 0;
	}

	public static Object addLandline(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Contact contact = new Landline(InputHandler.getNextLine(LANDLINE_PROMPT));
		contactService.create(contact, person);
		System.out.println(String.format(
			"Successfully added \"%s\"  to Person ID [%d] \"%s\"!", contact, 
			person.getId(), person.getName()));
		return 0;
	}

	public static Object addEmail(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Contact contact = new Email(InputHandler.getNextLine(EMAIL_PROMPT));
		contactService.create(contact, person);
		System.out.println(String.format(
			"Successfully added \"%s\"  to Person ID [%d] \"%s\"!", contact, 
			person.getId(), person.getName()));
		return 0;
	}

	public static Object addMobileNumber(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Contact contact = new MobileNumber(InputHandler.getNextLine(MOBILE_NUMBER_PROMPT));
		contactService.create(contact, person);
		System.out.println(String.format(
			"Successfully added \"%s\"  to Person ID [%d] \"%s\"!", contact, 
			person.getId(), person.getName()));
		return 0;
	}

	public static Object updateContact(Object parameter) throws Exception {
		Person person = (Person) parameter;
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
		
		return 0;
	}

	public static Object deleteContact(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Set<Contact> contacts = person.getContacts();

		System.out.println("-------------------");
		if (contacts.isEmpty()) {
			System.out.println("There are no contacts to delete.");
		}
		else {
			String listOfContacts = contacts.stream()
				.map(contact -> contact.toString())
				.collect(Collectors.joining("\n"));

			Integer contactId = InputHandler.getNextLine(
				String.format("%s\n%s\nContact ID: ", DELETE_CONTACT_PROMPT, listOfContacts), Integer::valueOf);

			contactService.delete(contactId);
			System.out.println(String.format(
				"Successfully removed Contact ID \"%d\"  from Person ID [%d] \"%s\"!", contactId, 
				person.getId(), person.getName()));	
		}
		System.out.println("-------------------");
		
		return 0;
	}

	public static Object listRoles(Object parameter) throws Exception {
		Person person = (Person) parameter;
		System.out.println("-------------------");

		List<Role> roles = roleService.list(person);
		if (roles.isEmpty()) {
			System.out.println(String.format("There are no roles assigned to Person ID [%d] \"%s\"", 
				person.getId(), person.getName()));
		}
		else {	
			System.out.println(String.format("Person ID [%d] \"%s\" has the following roles:", 
				person.getId(), person.getName()));
			roles.stream()
				.map(role -> role.toString())
				.forEach(System.out::println);
		}

		System.out.println("-------------------");
		
		return 0;
	}

	public static Object addRole(Object parameter) throws Exception {
		Person person = (Person) parameter;
		List<Role> roles = roleService.listRolesNotBelongingTo(person);

		System.out.println("-------------------");
		if (roles.isEmpty()) {
			System.out.println("There are no available roles to assign.");
		}
		else {		
			String listOfRoles = roles.stream()
				.map(role -> role.toString())
				.collect(Collectors.joining("\n"));

			Integer roleId = InputHandler.getNextLine(
				String.format("%s\n%s\nRole ID: ", ADD_ROLE_PROMPT, listOfRoles), Integer::valueOf);
			
			roleService.addRoleToPerson(roleId, person);
			System.out.println(String.format(
				"Successfully added role ID \"%d\" to Person ID [%d] \"%s\"!", roleId, person.getId(),
				person.getName()));
		}		
		System.out.println("-------------------");		
		return 0;
	}

	public static Object removeRole(Object parameter) throws Exception {
		Person person = (Person) parameter;
		List<Role> roles = roleService.list(person);

		System.out.println("-------------------");
		if (roles.isEmpty()) {
			System.out.println("There are no assigned roles to remove.");
		}
		else {		
			String listOfRoles = roles.stream()
				.map(role -> role.toString())
				.collect(Collectors.joining("\n"));

			Integer roleId = InputHandler.getNextLine(
				String.format("%s\n%s\nRole ID: ", REMOVE_ROLE_PROMPT, listOfRoles), Integer::valueOf);

			roleService.removeRoleFromPerson(roleId, person);
			System.out.println(String.format(
				"Successfully removed role ID \"%d\" to Person ID [%d] \"%s\"!", roleId, 
				person.getId(), person.getName()));
		}
		System.out.println("-------------------");
		
		return 0;
	}

	public static Object delete(Object parameter) throws Exception {			
		Integer personId = InputHandler.getNextLine(DELETE_PROMPT, Integer::valueOf);
		personService.delete(personId);
		System.out.println(String.format("Successfully deleted the person ID \"%d\"!", personId));
		return 0;
	}
}