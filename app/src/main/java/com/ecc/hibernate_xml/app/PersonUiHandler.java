package com.ecc.hibernate_xml.app;

import java.util.List;
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
	private static final String DATE_HIRED_PROMPT = "Please enter date hired (yyyy-MM-dd): ";

	private static final String NO_PERSONS_MESSAGE = "There are no persons.";
	private static final String CREATE_SUCCESS_MESSAGE = "Successfully created the person ID [%d] \"%s\"!";
	private static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated person's %s to \"%s\"!";
	private static final String DELETE_SUCCESS_MESSAGE = "Successfully deleted the person ID \"%d\"!";
	private static final String EMPLOYMENT_SUCCESS_MESSAGE = "Successfully updated person's employment status!";
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final PersonService personService = new PersonService();

	public static Object listByDateHired(Object parameter) {		
		list(personService.listPersonsByDateHired());
		return 0;
	}

	public static Object listByLastName(Object parameter) {		
		list(personService.listPersonsByLastName());
		return 0;
	}

	public static Object listByGWA(Object parameter) {		
		list(personService.listPersonsByGwa());
		return 0;
	}

	private static void list(List<Person> persons) {		
		System.out.println("-------------------");

		if (persons.isEmpty()) {
			System.out.println(NO_PERSONS_MESSAGE);
		}
		else {	
			persons.stream().forEach(System.out::println);
		}

		System.out.println("-------------------");
	}

	public static Object create(Object parameter) throws Exception {	
		Name name = new Name();
		name.setLastName(InputHandler.getNextLineREPL(LAST_NAME_PROMPT, PersonService::validateLastName));
		name.setFirstName(InputHandler.getNextLineREPL(FIRST_NAME_PROMPT, PersonService::validateFirstName));
		name.setMiddleName(InputHandler.getNextLineREPL(MIDDLE_NAME_PROMPT, PersonService::validateMiddleName));

		Person person = new Person(name);
		personService.create(person);

		String successMessage = String.format(CREATE_SUCCESS_MESSAGE, person.getId(), person.getName());
		System.out.println(successMessage);	

		return person;
	}

	public static Object update(Object parameter) throws Exception {
		Integer personId = InputHandler.getNextLine(UPDATE_PROMPT, Integer::valueOf);
		return personService.get(personId);	
	}

	public static Object changeName(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Name name = person.getName();

		name.setTitle(InputHandler.getNextLineREPL(TITLE_PROMPT, PersonService::validateTitle));
		name.setLastName(InputHandler.getNextLineREPL(LAST_NAME_PROMPT, PersonService::validateLastName));
		name.setFirstName(InputHandler.getNextLineREPL(FIRST_NAME_PROMPT, PersonService::validateFirstName));
		name.setMiddleName(InputHandler.getNextLineREPL(MIDDLE_NAME_PROMPT, PersonService::validateMiddleName));
		name.setSuffix(InputHandler.getNextLineREPL(SUFFIX_PROMPT, PersonService::validateSuffix));

		person.setName(name);
		personService.update(person);

		String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, "name", name);
		System.out.println(successMessage);
		return 0;
	}

	public static Object changeAddress(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Address.Factory addressFactory = new Address.Factory();
		InputHandler.getNextLineREPL(STREET_NUMBER_PROMPT, addressFactory::setStreetNumber);
		InputHandler.getNextLineREPL(BARANGAY_PROMPT, input -> 
			addressFactory.setBarangay(Integer.valueOf(input)));
		InputHandler.getNextLineREPL(MUNICIPALITY_PROMPT, addressFactory::setMunicipality);
		InputHandler.getNextLineREPL(ZIP_CODE_PROMPT, input -> 
			addressFactory.setZipCode(Integer.valueOf(input)));

		Address address = addressFactory.build();
		person.setAddress(address);
		personService.update(person);

		String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, "address", address);
		System.out.println(successMessage);
		return 0;
	}

	public static Object changeBirthday(Object parameter) throws Exception {
		Person person = (Person) parameter;
		Date birthday = InputHandler.getNextLineREPL(BIRTHDAY_PROMPT, dateFormat::parse);

		person.setBirthday(birthday);
		personService.update(person);

		String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, "birthday", dateFormat.format(birthday));
		System.out.println(successMessage);
		return 0;
	}

	public static Object changeGWA(Object parameter) throws Exception {
		Person person = (Person) parameter;
		BigDecimal GWA = InputHandler.getNextLineREPL(GWA_PROMPT, BigDecimal::new);

		person.setGWA(GWA);
		personService.update(person);

		String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, "GWA", GWA);
		System.out.println(successMessage);
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
			Date dateHired = InputHandler.getNextLineREPL(DATE_HIRED_PROMPT, dateFormat::parse);
			person.setDateHired(dateHired);
		}

		personService.update(person);
		System.out.println(EMPLOYMENT_SUCCESS_MESSAGE);
		return 0;
	}

	public static Object delete(Object parameter) throws Exception {			
		Integer personId = InputHandler.getNextLine(DELETE_PROMPT, Integer::valueOf);

		personService.delete(personId);

		String successMessage = String.format(DELETE_SUCCESS_MESSAGE, personId);
		System.out.println(successMessage);
		return 0;
	}
}