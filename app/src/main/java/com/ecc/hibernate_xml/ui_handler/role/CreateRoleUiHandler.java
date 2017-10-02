package com.ecc.hibernate_xml.ui_handler.role;

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
		roleService.createRole(roleName);
		System.out.println(String.format("Successfully created the role \"%s\"!", roleName));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}