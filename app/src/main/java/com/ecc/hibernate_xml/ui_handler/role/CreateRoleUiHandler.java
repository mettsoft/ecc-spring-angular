package com.ecc.hibernate_xml.ui_handler.role;

import java.io.Serializable;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
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
		String roleName = InputHandler.getNextLine(PROMPT);
		Serializable roleId = roleService.createRole(roleName);
		System.out.println(String.format("Successfully created the role \"%s\" with ID \"%s\"!", 
			roleName, roleId));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}