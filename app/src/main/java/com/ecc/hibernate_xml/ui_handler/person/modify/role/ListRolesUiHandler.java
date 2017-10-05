package com.ecc.hibernate_xml.ui_handler.person.modify.role;

import com.ecc.hibernate_xml.ui_handler.UiHandler;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.model.Role;

public class ListRolesUiHandler extends UiHandler {

	private RoleService roleService;
	private Person person;

	public ListRolesUiHandler(String operationName, Person person) {
		super(operationName);
		this.roleService = new RoleService();
		this.person = person;
	}

	@Override 
	public void onHandle() throws Exception {
		System.out.println("-------------------");
		System.out.println(String.format("Person ID [%d] \"%s\" has the following roles:", 
			person.getId(), person.getName()));
		roleService.listRoles(person).stream()
			.map(role -> String.format("[ID=%d] %s", role.getId(), role.getName()))
			.forEach(System.out::println);
		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}