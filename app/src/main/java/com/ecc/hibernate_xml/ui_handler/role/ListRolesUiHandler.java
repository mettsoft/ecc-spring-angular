package com.ecc.hibernate_xml.ui_handler.role;

import java.util.List;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.model.Role;

public class ListRolesUiHandler extends UiHandler {
	private RoleService roleService;

	public ListRolesUiHandler(String operationName) {
		super(operationName);
		roleService = new RoleService();
	}

	@Override 
	public void onHandle() throws Exception {
		System.out.println("-------------------");

		List<Role> roles = roleService.listRoles();
		if (roles.isEmpty()) {
			System.out.println("There are no roles.");
		}
		else {	
			roleService.listRoles().stream().forEach(System.out::println);
		}

		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}