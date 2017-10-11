package com.ecc.hibernate_xml.ui_handler.person.modify.role;

import java.util.List;

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

		List<Role> roles = roleService.list(person);
		if (roles.isEmpty()) {
			System.out.println(String.format("There are no roles assigned to Person ID [%d] \"%s\"", 
				person.getId(), person.getName()));
		}
		else {	
			System.out.println(String.format("Person ID [%d] \"%s\" has the following roles:", 
				person.getId(), person.getName()));
			roles.stream()
				.map(role -> role.toString())
				.forEach(System.out::println);
		}

		System.out.println("-------------------");
	}

	@Override 
	protected Boolean relinquishControl() {
		return true;
	}
}