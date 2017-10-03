package com.ecc.hibernate_xml.ui_handler.person.update.role;

import java.util.stream.Collectors;

import com.ecc.hibernate_xml.ui_handler.CompositeUiHandler;
import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Role;

public class RemoveRoleUiHandler extends UiHandler {

	private static final String PROMPT = "Please choose role to remove from the following: ";

	private PersonService personService;
	private RoleService roleService;
	private Person person;

	public RemoveRoleUiHandler(String operationName, Person person) {
		super(operationName);
		this.personService = new PersonService();
		this.roleService = new RoleService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {

		String listOfRoles = roleService.listRoles(person).stream()
			.map(role -> String.format("[ID=%d] %s", role.getId(), role.getName()))
			.collect(Collectors.joining("\n"));

		Integer roleId = InputHandler.getNextLine(
			String.format("%s\n%s\nRole ID: ", PROMPT, listOfRoles), Integer::valueOf);

		personService.removeRoleFromPerson(roleId, person);
		System.out.println(String.format(
			"Successfully removed role ID \"%d\" to Person \"%s\"!", roleId, person.getName()));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}