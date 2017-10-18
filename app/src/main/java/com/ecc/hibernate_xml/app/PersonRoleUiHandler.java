package com.ecc.hibernate_xml.app;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.util.app.InputHandler;

public class PersonRoleUiHandler {
	private static final String ADD_ROLE_PROMPT = "Please choose role to add from the following:\n%s\nRole ID: ";
	private static final String REMOVE_ROLE_PROMPT = "Please choose role to remove from the following:\n%s\nRole ID: ";

	private static final String NO_ROLES_MESSAGE = "There are no roles assigned to Person ID [%d] \"%s\".";
	private static final String NO_ROLES_TO_ASSIGN = "There are no available roles to assign.";
	private static final String NO_ROLES_TO_REMOVE = "There are no assigned roles to remove.";
	private static final String LIST_ROLES_HEADER = "Person ID [%d] \"%s\" has the following roles:";
	private static final String ADD_SUCCESS_MESSAGE = "Successfully added role ID \"%d\" to Person ID [%d] \"%s\"!";
	private static final String REMOVE_SUCCESS_MESSAGE = "Successfully removed role ID \"%d\" to Person ID [%d] \"%s\"!";

	private final RoleService roleService = new RoleService();

	public void list(Object parameter) {		
		Person person = (Person) parameter;
		System.out.println("-------------------");

		List<RoleDTO> roles = roleService.list(person);
		if (roles.isEmpty()) {
			System.out.println(String.format(NO_ROLES_MESSAGE, 
				person.getId(), person.getName()));
		}
		else {	
			System.out.println(String.format(LIST_ROLES_HEADER, 
				person.getId(), person.getName()));

			roles.stream()
				.map(role -> role.toString())
				.forEach(System.out::println);
		}

		System.out.println("-------------------");
	}

	public void add(Object parameter) throws Exception {
		Person person = (Person) parameter;
		List<RoleDTO> roles = roleService.listRolesNotBelongingTo(person);

		System.out.println("-------------------");
		if (roles.isEmpty()) {
			System.out.println(NO_ROLES_TO_ASSIGN);
		}
		else {		
			String listOfRoles = roles.stream()
				.map(role -> role.toString())
				.collect(Collectors.joining("\n"));
			String userPrompt = String.format(ADD_ROLE_PROMPT, listOfRoles);
			Integer roleId = InputHandler.getNextLine(userPrompt, Integer::valueOf);
			
			roleService.addRoleToPerson(roleId, person);

			String successMessage = String.format(ADD_SUCCESS_MESSAGE, roleId, person.getId(),
				person.getName());
			System.out.println(successMessage);
		}		

		System.out.println("-------------------");
	}

	public void remove(Object parameter) throws Exception {	
		Person person = (Person) parameter;
		List<RoleDTO> roles = roleService.list(person);

		System.out.println("-------------------");
		if (roles.isEmpty()) {
			System.out.println(NO_ROLES_TO_REMOVE);
		}
		else {		
			String listOfRoles = roles.stream()
				.map(role -> role.toString())
				.collect(Collectors.joining("\n"));
			String userPrompt = String.format(REMOVE_ROLE_PROMPT, listOfRoles);
			Integer roleId = InputHandler.getNextLine(userPrompt, Integer::valueOf);

			roleService.removeRoleFromPerson(roleId, person);

			String successMessage = String.format(REMOVE_SUCCESS_MESSAGE, roleId, person.getId(),
				person.getName());
			System.out.println(successMessage);
		}
		
		System.out.println("-------------------");
	}
}