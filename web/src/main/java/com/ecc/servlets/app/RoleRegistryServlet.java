package com.ecc.servlets.app;

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

import com.ecc.servlets.util.app.NumberUtils;

import com.ecc.servlets.dto.RoleDTO;
import com.ecc.servlets.service.RoleService;
import com.ecc.servlets.util.app.ExceptionHandler;
import com.ecc.servlets.util.app.TemplateEngine;
import com.ecc.servlets.util.validator.ValidationException;

public class RoleRegistryServlet extends HttpServlet {
	private static final String VIEW_TEMPLATE = "<html><head><title>Role | Person Registry System</title><style>div.container {display: inline-flex;width: 100%;}div.container div {width: 50%;margin: 16px;}label {display: block;}table, th, td {border: 1px solid black;}</style></head><body><a href=\"/\">Go to Person Registry</a><div class=\"container\"><div><h3>:message</h3><h3>:header</h3><form action=\"/role\" method=\"POST\"><input type=\"hidden\" name=\"mode\" value=\":mode\"><input type=\"hidden\" name=\"id\" value=\":id\"><label for=\"name\">Enter the role name:</label><input type=\"text\" id=\"name\" name=\"name\" value=\":name\"><button>Submit</button></form></div><div><h3>Roles</h3>:dataTable		</div>			</div></body></html>";

	private static final String SERVLET_PATH = "/role";

	private static final Integer MODE_CREATE = 1;
	private static final Integer MODE_UPDATE = 2;
	private static final Integer MODE_DELETE = 3;

	private static final String QUERY_PARAMETER_ENCODED_RESPONSE = "m";

	private static final String QUERY_PARAMETER_MODE = "mode";
	private static final String QUERY_PARAMETER_ROLE_ID = "id";
	private static final String QUERY_PARAMETER_ROLE_NAME = "name";

	private static final String VIEW_PARAMETER_MESSAGE = ":message";
	private static final String VIEW_PARAMETER_HEADER = ":header";
	private static final String VIEW_PARAMETER_DATATABLE = ":dataTable";
	
	private static final String CREATE_SUCCESS_MESSAGE = "Successfully created the role \"%s\"!";
	private static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated the role \"%s\"!";
	private static final String DELETE_SUCCESS_MESSAGE = "Successfully deleted a role!";
	private static final String AFFECTED_PERSONS_MESSAGE = "Please take note that the following person IDs are affected: [%s].";

	private RoleService roleService;

	@Override
	public void init() throws ServletException {
		roleService = new RoleService();
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
				parameters.put(":" + QUERY_PARAMETER_MODE, MODE_CREATE);
			}
			else {
				RoleDTO role = roleService.get(NumberUtils.createInteger(roleId));
				parameters.put(VIEW_PARAMETER_HEADER, "Update existing role");
				parameters.put(":" + QUERY_PARAMETER_ROLE_ID, role.getId());
				parameters.put(":" + QUERY_PARAMETER_ROLE_NAME, role.getName());
				parameters.put(":" + QUERY_PARAMETER_MODE, MODE_UPDATE);
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
				response.sendRedirect(String.format("%s?%s=%d", SERVLET_PATH, QUERY_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_CREATE, role.getId())));
			}
			else if (mode.equals(MODE_UPDATE.toString())){
				parameters.put(VIEW_PARAMETER_HEADER, "Update existing role");

				role = roleService.get(NumberUtils.createInteger(rawRoleId));
				role.setName(request.getParameter(QUERY_PARAMETER_ROLE_NAME));
				roleService.validate(role);
				roleService.update(role);
				response.sendRedirect(String.format("%s?%s=%d", SERVLET_PATH, QUERY_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_UPDATE, role.getId())));
			}	
			else if (mode.equals(MODE_DELETE.toString())) {
				Integer roleId = NumberUtils.createInteger(rawRoleId);
				roleService.delete(roleId);	
				response.sendRedirect(String.format("%s?%s=%d", SERVLET_PATH, QUERY_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_DELETE, roleId)));
			}
		}
		catch (Exception cause) {
			if (cause instanceof ValidationException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				parameters.put(":" + QUERY_PARAMETER_MODE, mode);
			}
			else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				parameters.put(VIEW_PARAMETER_HEADER, "Create new role");
				parameters.put(":" + QUERY_PARAMETER_MODE, MODE_CREATE);
			}
			parameters.put(VIEW_PARAMETER_MESSAGE, ExceptionHandler.printException(cause));
			parameters.put(":" + QUERY_PARAMETER_ROLE_ID, rawRoleId);
			parameters.put(":" + QUERY_PARAMETER_ROLE_NAME, request.getParameter(QUERY_PARAMETER_ROLE_NAME));
			parameters.put(VIEW_PARAMETER_DATATABLE, createDataTable(templateEngine));
			templateEngine.render(VIEW_TEMPLATE, parameters);
		}
	}

	private String createMessage(HttpServletRequest request) throws Exception {
		String message = null;
		String rawEncodedResponse = request.getParameter(QUERY_PARAMETER_ENCODED_RESPONSE);
		if (rawEncodedResponse != null) {
			Integer encodedResponse = NumberUtils.createInteger(rawEncodedResponse);
			Integer roleId = decodeRoleId(encodedResponse);
			Integer mode = decodeMode(encodedResponse);
			RoleDTO role = null;
		
			if (mode == MODE_CREATE) {
				role = roleService.get(roleId);
				message = String.format(CREATE_SUCCESS_MESSAGE, role.getName());	
			}
			else if (mode == MODE_UPDATE) {
				role = roleService.get(roleId);
				message = String.format(UPDATE_SUCCESS_MESSAGE, role.getName());
				if (role.getPersons().size() > 0) {
					String personIds = role.getPersons().stream()
						.map(person -> person.getId().toString())
						.collect(Collectors.joining(", "));
					message += " " + String.format(AFFECTED_PERSONS_MESSAGE, personIds);
				}
			}
			else if (mode == MODE_DELETE) {
				message = DELETE_SUCCESS_MESSAGE;
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

		return templateEngine.generateTable(headers, data, SERVLET_PATH);		
	}
	
	private Integer encodeResponse(Integer mode, Integer roleId) {
		return roleId << 8 | mode & 0xff;
	}
}