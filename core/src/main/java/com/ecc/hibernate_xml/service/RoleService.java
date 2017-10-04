package com.ecc.hibernate_xml.service;

import java.util.List;

import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.dao.RoleDao;
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

	public void createRole(String roleName) throws Exception {
		roleDao.createRole(new Role(roleName));		
	}

	public void updateRole(Integer roleId, String roleName) throws Exception {
		dao.updateRole(roleId, new Role(roleName));
	}

	public void deleteRole(Integer roleId) throws Exception {
		roleDao.deleteRole(roleDao.getRole(roleId));
	}
}