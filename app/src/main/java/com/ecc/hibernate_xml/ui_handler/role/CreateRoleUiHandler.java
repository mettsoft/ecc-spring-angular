package com.ecc.hibernate_xml.ui_handler.role;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.app.InputHandler;
import com.ecc.hibernate_xml.service.RoleService;

public class CreateRoleUiHandler extends UiHandler {
	private static final String PROMPT = "Please enter a new role: ";

	private RoleService roleService;

	public CreateRoleUiHandler(String operationName) {
		super(operationName);
		roleService = new RoleService();
	}

	@Override 
	public void onHandle() throws Exception {
		Role role = InputHandler.getNextLineREPL(PROMPT, Role::new);
		roleService.create(role);
		System.out.println(String.format("Successfully created the role \"%s\" with ID \"%s\"!", 
			role.getName(), role.getId()));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}