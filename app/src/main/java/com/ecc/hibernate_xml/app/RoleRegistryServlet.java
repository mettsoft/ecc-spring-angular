package com.ecc.hibernate_xml.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.util.app.ExceptionHandler;
import com.ecc.hibernate_xml.util.app.TemplateEngine;
import com.ecc.hibernate_xml.util.dao.HibernateUtility;
import com.ecc.hibernate_xml.util.validator.ValidationException;

public class RoleRegistryServlet extends HttpServlet {
	private String VIEW_TEMPLATE;

	private static final String CONTEXT_PATH = "/role";

	private static final Integer MODE_CREATE = 1;
	private static final Integer MODE_UPDATE = 2;
	private static final Integer MODE_DELETE = 3;

	private static final String QUERY_PARAMETER_ROLE_ID = "id";
	private static final String QUERY_PARAMETER_ROLE_NAME = "name";
	private static final String QUERY_PARAMETER_MODE = "mode";
	private static final String QUERY_PARAMETER_ENCODED_RESPONSE = "m";

	private static final String VIEW_PARAMETER_MESSAGE = ":message";
	private static final String VIEW_PARAMETER_HEADER = ":header";
	private static final String VIEW_PARAMETER_MODE = ":mode";
	private static final String VIEW_PARAMETER_ROLE_ID = ":roleId";
	private static final String VIEW_PARAMETER_ROLE_NAME = ":roleName";
	private static final String VIEW_PARAMETER_DATATABLE = ":dataTable";
	
	private static final String CREATE_SUCCESS_MESSAGE = "Successfully created the role ID \"%d\" with \"%s\"!";
	private static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated the role ID \"%d\" with \"%s\"!";
	private static final String DELETE_SUCCESS_MESSAGE = "Successfully deleted the role ID \"%d\"!";
	private static final String AFFECTED_PERSONS_MESSAGE = "Please take note that the following person IDs are affected: [%s].";

	private RoleService roleService;

	@Override
	public void init() throws ServletException {
		VIEW_TEMPLATE = getServletContext().getRealPath("/") + "/role-registry.template.html";
		roleService = new RoleService();
		HibernateUtility.initializeSessionFactory();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		TemplateEngine templateEngine = new TemplateEngine(response.getWriter());
		Map<String, Object> parameters = new HashMap<>();
		String roleId = request.getParameter(QUERY_PARAMETER_ROLE_ID);

		try {
			parameters.put(VIEW_PARAMETER_MESSAGE, createMessage(request));
			if (roleId == null) {
				parameters.put(VIEW_PARAMETER_HEADER, "Create new role");
				parameters.put(VIEW_PARAMETER_MODE, MODE_CREATE);
			}
			else {
				RoleDTO role = roleService.get(Integer.valueOf(roleId));
				parameters.put(VIEW_PARAMETER_HEADER, "Update existing role");
				parameters.put(VIEW_PARAMETER_ROLE_ID, role.getId());
				parameters.put(VIEW_PARAMETER_ROLE_NAME, role.getName());
				parameters.put(VIEW_PARAMETER_MODE, MODE_UPDATE);
			}
		}
		catch (Exception cause) {
			parameters.put(VIEW_PARAMETER_MESSAGE, ExceptionHandler.printException(cause));
		}
		finally {
			parameters.put(VIEW_PARAMETER_DATATABLE, createDataTable(templateEngine));
			templateEngine.render(VIEW_TEMPLATE, parameters);
		}
	}

	private String createMessage(HttpServletRequest request) throws Exception {
		String message = null;
		String rawEncodedResponse = request.getParameter(QUERY_PARAMETER_ENCODED_RESPONSE);
		if (rawEncodedResponse != null) {
			Integer encodedResponse = Integer.valueOf(rawEncodedResponse);
			Integer roleId = decodeRoleId(encodedResponse);
			Integer mode = decodeMode(encodedResponse);
			RoleDTO role = null;
		
			if (mode == MODE_CREATE) {
				role = roleService.get(roleId);
				message = String.format(CREATE_SUCCESS_MESSAGE, role.getId(), role.getName());	
			}
			else if (mode == MODE_UPDATE) {
				role = roleService.get(roleId);
				message = String.format(UPDATE_SUCCESS_MESSAGE, role.getId(), role.getName());
				if (role.getPersons().size() > 0) {
					String personIds = role.getPersons().stream()
						.map(person -> person.getId().toString())
						.collect(Collectors.joining(", "));
					message += " " + String.format(AFFECTED_PERSONS_MESSAGE, personIds);
				}
			}
			else if (mode == MODE_DELETE) {
				message = String.format(DELETE_SUCCESS_MESSAGE, roleId);
			}			
		}
		return message;
	}

	private Integer decodeRoleId(Integer encodedResponse) {
		return encodedResponse >> 8;
	}

	private Integer decodeMode(Integer encodedResponse) {
		return encodedResponse & 0xff;
	}

	private String createDataTable(TemplateEngine templateEngine) {
		List<String> headers = Arrays.asList("ID", "Role");
		List<List<String>> data = roleService.list().stream()
			.map(role -> Arrays.asList(role.getId().toString(), role.getName()))
			.collect(Collectors.toList());

		return templateEngine.renderTable(headers, data, CONTEXT_PATH, CONTEXT_PATH);		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		TemplateEngine templateEngine = new TemplateEngine(response.getWriter());
		Map<String, Object> parameters = new HashMap<>();

		RoleDTO role = null;
		String rawRoleId = request.getParameter(QUERY_PARAMETER_ROLE_ID);
		String mode = request.getParameter(QUERY_PARAMETER_MODE);

		try {
			if (mode.equals(MODE_CREATE.toString())) {
				parameters.put(VIEW_PARAMETER_HEADER, "Create new role");

				role = new RoleDTO();
				role.setName(request.getParameter(QUERY_PARAMETER_ROLE_NAME));
				roleService.validate(role);
				role.setId((Integer) roleService.create(role));
				response.sendRedirect(String.format("%s?%s=%d", CONTEXT_PATH, QUERY_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_CREATE, role.getId())));
			}
			else if (mode.equals(MODE_UPDATE.toString())){
				parameters.put(VIEW_PARAMETER_HEADER, "Update existing role");

				role = roleService.get(Integer.valueOf(rawRoleId));
				role.setName(request.getParameter(QUERY_PARAMETER_ROLE_NAME));
				roleService.validate(role);
				roleService.update(role);
				response.sendRedirect(String.format("%s?%s=%d", CONTEXT_PATH, QUERY_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_UPDATE, role.getId())));
			}	
			else if (mode.equals(MODE_DELETE.toString())) {
				Integer roleId = Integer.valueOf(rawRoleId);
				roleService.delete(roleId);	
				response.sendRedirect(String.format("%s?%s=%d", CONTEXT_PATH, QUERY_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_DELETE, roleId)));
			}
		}
		catch (Exception cause) {
			if (cause instanceof ValidationException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				parameters.put(VIEW_PARAMETER_MODE, mode);
			}
			else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				parameters.put(VIEW_PARAMETER_HEADER, "Create new role");
				parameters.put(VIEW_PARAMETER_MODE, MODE_CREATE);
			}
			parameters.put(VIEW_PARAMETER_MESSAGE, ExceptionHandler.printException(cause));
			parameters.put(VIEW_PARAMETER_ROLE_ID, rawRoleId);
			parameters.put(VIEW_PARAMETER_ROLE_NAME, request.getParameter(QUERY_PARAMETER_ROLE_NAME));
			parameters.put(VIEW_PARAMETER_DATATABLE, createDataTable(templateEngine));
			templateEngine.render(VIEW_TEMPLATE, parameters);
		}
	}
	
	private Integer encodeResponse(Integer mode, Integer roleId) {
		return roleId << 8 | mode & 0xff;
	}

	@Override
	public void destroy() {
		HibernateUtility.closeSessionFactory();
	}
}