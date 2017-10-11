package com.ecc.hibernate_xml.ui_handler.role;

import java.util.stream.Collectors;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.app.InputHandler;
import com.ecc.hibernate_xml.service.RoleService;

public class UpdateRoleUiHandler extends UiHandler {
	private static final String ID_PROMPT = "Please enter the role ID you wish to replace: ";
	private static final String NAME_PROMPT = "Please enter the new role: "; 

	private RoleService roleService;

	public UpdateRoleUiHandler(String operationName) {
		super(operationName);
		roleService = new RoleService();
	}

	@Override 
	public void onHandle() throws Exception {
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
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}