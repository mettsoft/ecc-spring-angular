package com.ecc.hibernate_xml.ui_handler.role;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.util.InputHandler;
import com.ecc.hibernate_xml.service.RoleService;

public class DeleteRoleUiHandler extends UiHandler {
	private static final String PROMPT = "Please enter the role ID you wish to delete: ";

	private RoleService roleService;

	public DeleteRoleUiHandler(String operationName) {
		super(operationName);
		roleService = new RoleService();
	}

	@Override 
	public void onHandle() throws Exception {
		Integer roleId = InputHandler.getNextLine(PROMPT, Integer::valueOf);
		roleService.deleteRole(roleId);
		System.out.println(String.format("Successfully deleted the role ID \"%d\"!", roleId));
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}