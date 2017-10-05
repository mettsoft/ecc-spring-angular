package com.ecc.hibernate_xml.service;

import java.io.Serializable;
import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.model.ModelException;
import com.ecc.hibernate_xml.model.Role;
import com.ecc.hibernate_xml.model.Person;

public class RoleService {
	private RoleDao roleDao;

	public RoleService() {
		roleDao = new RoleDao();
	}

	public List<Role> listRoles() {
		return roleDao.listRoles();
	}

	public List<Role> listRoles(Person person) {
		return roleDao.listRoles(person);
	}

	public Serializable createRole(String roleName) throws DaoException, ModelException {
		return roleDao.createRole(new Role(roleName));		
	}

	public void updateRole(Integer roleId, String roleName) throws DaoException, ModelException {
		Role role = new Role(roleName);
		role.setId(roleId);
		roleDao.updateRole(role);
	}

	public void deleteRole(Integer roleId) throws DaoException {
		roleDao.deleteRole(roleDao.getRole(roleId));
	}
}