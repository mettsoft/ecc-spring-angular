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
import com.ecc.hibernate_xml.service.PersonService;
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

	private static PersonService personService = new PersonService();

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

	public static Object delete(Object parameter) throws Exception {			
		Integer personId = InputHandler.getNextLine(DELETE_PROMPT, Integer::valueOf);
		personService.delete(personId);
		System.out.println(String.format("Successfully deleted the person ID \"%d\"!", personId));
		return 0;
	}
}