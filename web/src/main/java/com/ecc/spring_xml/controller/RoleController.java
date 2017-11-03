package com.ecc.spring_xml.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.ecc.spring_xml.service.RoleService;
import com.ecc.spring_xml.util.app.NumberUtils;

public class RoleController extends MultiActionController {
	private static final String QUERY_PARAMETER_ROLE_ID = "id";
	private static final String VIEW_PARAMETER_HEADER = "headerMessage";
	private static final String VIEW_PARAMETER_DATA = "data";

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("role");

		// TODO: Consider throwing the error if ParseException of id.
		Integer roleId = NumberUtils.createInteger(request.getParameter(QUERY_PARAMETER_ROLE_ID));
		if (roleId == null) {
			modelView.addObject(VIEW_PARAMETER_HEADER, "Create new role");
		}
		else {
			modelView.addObject(VIEW_PARAMETER_HEADER, "Update existing role");	
		}

		modelView.addObject(VIEW_PARAMETER_DATA, roleService.list());
		return modelView;
	}

	// Custom Exception Handler.
	// public ModelAndView anyMeaningfulName(HttpServletRequest request, HttpServletResponse response, ExceptionClass exception);
}