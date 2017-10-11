package com.ecc.hibernate_xml.app;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.util.InputHandler;

public class RoleUiHandler {
	private static RoleService roleService = new RoleService();
	private static final String ID_PROMPT = "Please enter the role ID you wish to replace: ";
	private static final String NAME_PROMPT = "Please enter the new role: "; 
	private static final String DELETE_PROMPT = "Please enter the role ID you wish to delete: ";

	public static Object list(Object parameter) {		
		System.out.println("-------------------");

		List<Role> roles = roleService.list();
		if (roles.isEmpty()) {
			System.out.println("There are no roles.");
		}
		else {	
			roles.stream().forEach(System.out::println);
		}

		System.out.println("-------------------");
		return 0;
	}

	public static Object create(Object parameter) throws Exception {			
		Role role = InputHandler.getNextLineREPL(NAME_PROMPT, Role::new);
		roleService.create(role);
		System.out.println(String.format("Successfully created the role \"%s\" with ID \"%s\"!", 
			role.getName(), role.getId()));
		return 0;
	}

	public static Object update(Object parameter) throws Exception {			
		Integer roleId = InputHandler.getNextLine(ID_PROMPT, Integer::valueOf);
		Role role = roleService.get(roleId);

		InputHandler.getNextLineREPL(NAME_PROMPT, input -> {
			role.setName(input);
			return 0;
		});

		roleService.update(role);

		String message = String.format("Successfully updated the role ID \"%d\" with \"%s\"!", 
			role.getId(), role.getName());

		if (role.getPersons().size() > 0) {
			String personIds = role.getPersons()
				.stream()
				.map(person -> person.getId().toString())
				.collect(Collectors.joining(", "));

			message += String.format(
				" Please take note that the following person IDs are affected: [%s].",
				personIds);
		}
		System.out.println(message);
		return 0;
	}

	public static Object delete(Object parameter) throws Exception  {			
		Integer roleId = InputHandler.getNextLine(DELETE_PROMPT, Integer::valueOf);
		roleService.delete(roleId);
		System.out.println(String.format("Successfully deleted the role ID \"%d\"!", roleId));
		return 0;
	}
}