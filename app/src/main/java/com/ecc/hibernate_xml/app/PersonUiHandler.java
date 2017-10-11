package com.ecc.hibernate_xml.app;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.util.InputHandler;

public class PersonUiHandler {
	private static PersonService personService = new PersonService();
	private static final String DELETE_PROMPT = "Please enter the person ID you wish to delete: ";

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
		return 0;
	}

	public static Object update(Object parameter) throws Exception {			
		return 0;
	}

	public static Object delete(Object parameter) throws Exception {			
		Integer personId = InputHandler.getNextLine(DELETE_PROMPT, Integer::valueOf);
		personService.delete(personId);
		System.out.println(String.format("Successfully deleted the person ID \"%d\"!", personId));
		return 0;
	}
}