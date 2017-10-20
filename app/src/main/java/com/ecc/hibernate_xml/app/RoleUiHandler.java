package com.ecc.hibernate_xml.app;

import java.util.List;
import java.util.stream.Collectors;

import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.util.app.InputHandler;

public class RoleUiHandler {
	private static final String UPDATE_PROMPT = "Please enter the role ID you wish to update: ";
	private static final String DELETE_PROMPT = "Please enter the role ID you wish to delete: ";
	private static final String NAME_PROMPT = "Please enter the new role: "; 

	private static final String NO_ROLES_MESSAGE = "There are no roles.";
	private static final String CREATE_SUCCESS_MESSAGE = "Successfully created the role ID \"%d\" with \"%s\"!";
	private static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated the role ID \"%d\" with \"%s\"!";
	private static final String DELETE_SUCCESS_MESSAGE = "Successfully deleted the role ID \"%d\"!";
	private static final String AFFECTED_PERSONS_MESSAGE = "Please take note that the following person IDs are affected: [%s].";

	private final RoleService roleService = new RoleService();

	public void list() {		
		List<RoleDTO> roles = roleService.list();
		if (roles.isEmpty()) {
			System.out.println(NO_ROLES_MESSAGE);
		}
		else {	
			roles.stream().forEach(System.out::println);
		}

		System.out.println("-------------------");
	}

	public void create() throws Exception {		
		RoleDTO role = new RoleDTO();	
		role.setName(InputHandler.getNextLineREPL(NAME_PROMPT, roleService::validateName));
		role.setId((Integer) roleService.create(role));

		String successMessage = String.format(CREATE_SUCCESS_MESSAGE, role.getId(), role.getName());
		System.out.println(successMessage);
	}

	public void update() throws Exception {			
		Integer roleId = InputHandler.getNextLine(UPDATE_PROMPT, Integer::valueOf);
		RoleDTO role = roleService.get(roleId);

		role.setName(InputHandler.getNextLineREPL(NAME_PROMPT, roleService::validateName));
		roleService.update(role);

		String successMessage = String.format(UPDATE_SUCCESS_MESSAGE, role.getId(), role.getName());

		if (role.getPersons().size() > 0) {
			String personIds = role.getPersons()
				.stream()
				.map(id -> id.toString())
				.collect(Collectors.joining(", "));

			successMessage += " " + String.format(AFFECTED_PERSONS_MESSAGE, personIds);
		}
		System.out.println(successMessage);
	}

	public void delete() throws Exception {			
		Integer roleId = InputHandler.getNextLine(DELETE_PROMPT, Integer::valueOf);
		roleService.delete(roleId);

		String successMessage = String.format(DELETE_SUCCESS_MESSAGE, roleId);
		System.out.println(successMessage);
	}
}