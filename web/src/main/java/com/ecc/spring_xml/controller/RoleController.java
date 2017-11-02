package com.ecc.spring_xml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ecc.spring_xml.service.RoleService;

public class RoleController extends MultiActionController {
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("role");
	}
}