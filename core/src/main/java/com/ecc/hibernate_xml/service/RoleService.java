package com.ecc.hibernate_xml.service;

import com.ecc.hibernate_xml.dao.RoleDao;
import com.ecc.hibernate_xml.dao.DaoException;
import com.ecc.hibernate_xml.model.Role;

public class RoleService {
	private RoleDao dao;

	public RoleService() {
		dao = new RoleDao();
	}

	public void createRole(String roleName) throws DaoException {
		dao.createRole(new Role(roleName));		
	}

	public void updateRole(Integer roleId, String roleName) throws DaoException {
		dao.updateRole(roleId, new Role(roleName));
	}

	public void deleteRole(Integer roleId) throws DaoException {
		dao.deleteRole(roleId);
	}
}