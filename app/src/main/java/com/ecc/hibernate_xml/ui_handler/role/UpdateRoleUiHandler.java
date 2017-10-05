package com.ecc.hibernate_xml.ui_handler.role;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.util.InputHandler;
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
		Role role = roleService.getRole(roleId);

		InputHandler.getNextLineREPL(NAME_PROMPT, input -> {
			role.setName(input);
			return 0;
		});

		roleService.updateRole(role);
		System.out.println(String.format("Successfully updated the role ID \"%d\" with \"%s\"!", 
			role.getId(), role.getName()));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}