package com.ecc.hibernate_xml.ui_handler.person.modify.role;

import java.util.stream.Collectors;
import java.util.List;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Role;

public class RemoveRoleUiHandler extends UiHandler {
	private static final String PROMPT = "Please choose role to remove from the following: ";

	private RoleService roleService;
	private Person person;

	public RemoveRoleUiHandler(String operationName, Person person) {
		super(operationName);
		this.roleService = new RoleService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
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
				String.format("%s\n%s\nRole ID: ", PROMPT, listOfRoles), Integer::valueOf);

			roleService.removeRoleFromPerson(roleId, person);
			System.out.println(String.format(
				"Successfully removed role ID \"%d\" to Person ID [%d] \"%s\"!", roleId, 
				person.getId(), person.getName()));
		}
		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}