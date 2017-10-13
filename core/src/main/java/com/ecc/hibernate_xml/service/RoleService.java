package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;
import com.ecc.hibernate_xml.util.validator.ValidationException;
import com.ecc.hibernate_xml.util.validator.ModelValidator;

public class RoleService extends AbstractService<Role> {
	private static final Integer MAX_CHARACTERS = 20;
	private static final String MAX_LENGTH_ERROR_MESSAGE_TEMPLATE = "%s must not exceed " + MAX_CHARACTERS + " characters.";
	private static final String NOT_EMPTY_ERROR_MESSAGE_TEMPLATE = "%s cannot be empty.";

	private final RoleDao roleDao;

	public RoleService() {
		super(new RoleDao());
		roleDao = (RoleDao) dao;
	}

	public String validateName(String roleName) throws ValidationException {
		ModelValidator
			.create(roleName)
			.notEmpty(String.format(NOT_EMPTY_ERROR_MESSAGE_TEMPLATE, "Role name"))
			.maxLength(MAX_CHARACTERS, String.format(MAX_LENGTH_ERROR_MESSAGE_TEMPLATE, 
				"Role name"))
			.validate();
		return roleName;
	}

	public List<Role> list(Person person) {
		return roleDao.list(person);
	}

	public List<Role> listRolesNotBelongingTo(Person person) {
		return roleDao.listRolesNotBelongingTo(person);
	}

	public void addRoleToPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.get(roleId);
		role.getPersons().add(person);
		person.getRoles().add(role);
		roleDao.update(role);
	}

	public void removeRoleFromPerson(Integer roleId, Person person) throws DaoException {
		Role role = roleDao.get(roleId);
		role.getPersons().remove(person);
		person.getRoles().remove(role);
		roleDao.update(role);
	}
}