package com.ecc.hibernate_xml.app;

import java.io.IOException;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ecc.hibernate_xml.dto.PersonDTO;
import com.ecc.hibernate_xml.dto.RoleDTO;
import com.ecc.hibernate_xml.dto.NameDTO;
import com.ecc.hibernate_xml.dto.AddressDTO;
import com.ecc.hibernate_xml.service.PersonService;
import com.ecc.hibernate_xml.service.RoleService;
import com.ecc.hibernate_xml.util.app.DateUtils;
import com.ecc.hibernate_xml.util.app.NumberUtils;
import com.ecc.hibernate_xml.util.app.ExceptionHandler;
import com.ecc.hibernate_xml.util.app.TemplateEngine;
import com.ecc.hibernate_xml.util.dao.HibernateUtility;
import com.ecc.hibernate_xml.util.validator.ValidationException;

public class PersonRegistryServlet extends HttpServlet {
	private String VIEW_TEMPLATE;

	private static final String SERVLET_PATH = "/";

	private static final Integer MODE_CREATE = 1;
	private static final Integer MODE_UPDATE = 2;
	private static final Integer MODE_DELETE = 3;

	private static final String TEMPLATE_POST_QUERY_PARAMETERS = "<input type=\"hidden\" name=\"queryLastName\" value=\":queryLastName\"><input type=\"hidden\" name=\"queryRoleId\" value=\":queryRoleId\"><input type=\"hidden\" name=\"queryBirthday\" value=\":queryBirthday\"><input type=\"hidden\" name=\"queryOrderBy\" value=\":queryOrderBy\"><input type=\"hidden\" name=\"queryOrderType\" value=\":queryOrderType\">";

	private static final String FORM_PARAMETER_ENCODED_RESPONSE = "m";

	private static final String FORM_PARAMETER_MODE = "mode";
	private static final String FORM_PARAMETER_PERSON_ID = "id";

	private static final String FORM_PARAMETER_TITLE = "title";
	private static final String FORM_PARAMETER_LAST_NAME = "lastName";
	private static final String FORM_PARAMETER_FIRST_NAME = "firstName";
	private static final String FORM_PARAMETER_MIDDLE_NAME = "middleName";
	private static final String FORM_PARAMETER_SUFFIX = "suffix";

	private static final String FORM_PARAMETER_STREET_NUMBER = "streetNumber";
	private static final String FORM_PARAMETER_BARANGAY = "barangay";
	private static final String FORM_PARAMETER_MUNICIPALITY = "municipality";
	private static final String FORM_PARAMETER_ZIP_CODE = "zipCode";

	private static final String FORM_PARAMETER_BIRTHDAY = "birthday";
	private static final String FORM_PARAMETER_GWA = "GWA";
	private static final String FORM_PARAMETER_CURRENTLY_EMPLOYED = "currentlyEmployed";
	private static final String FORM_PARAMETER_DATE_HIRED = "dateHired";

	private static final String FORM_PARAMETER_PERSON_ROLE_IDS = "personRoleIds";

	private static final String QUERY_PARAMETER_PERSON_LAST_NAME = "queryLastName";
	private static final String QUERY_PARAMETER_ROLE_ID = "queryRoleId";
	private static final String QUERY_PARAMETER_BIRTHDAY = "queryBirthday";
	private static final String QUERY_PARAMETER_ORDER_BY = "queryOrderBy";
	private static final String QUERY_PARAMETER_ORDER_TYPE = "queryOrderType";

	// Contact
	// Role

	private static final String VIEW_PARAMETER_MESSAGE = ":message";
	private static final String VIEW_PARAMETER_HEADER = ":header";

	private static final String VIEW_PARAMETER_MODE = ":" + FORM_PARAMETER_MODE;
	private static final String VIEW_PARAMETER_PERSON_ID = ":" + FORM_PARAMETER_PERSON_ID;

	private static final String VIEW_PARAMETER_TITLE = ":" + FORM_PARAMETER_TITLE;
	private static final String VIEW_PARAMETER_LAST_NAME = ":" + FORM_PARAMETER_LAST_NAME;
	private static final String VIEW_PARAMETER_FIRST_NAME = ":" + FORM_PARAMETER_FIRST_NAME;
	private static final String VIEW_PARAMETER_MIDDLE_NAME = ":" + FORM_PARAMETER_MIDDLE_NAME;
	private static final String VIEW_PARAMETER_SUFFIX = ":" + FORM_PARAMETER_SUFFIX;

	private static final String VIEW_PARAMETER_STREET_NUMBER = ":" + FORM_PARAMETER_STREET_NUMBER;
	private static final String VIEW_PARAMETER_BARANGAY = ":" + FORM_PARAMETER_BARANGAY;
	private static final String VIEW_PARAMETER_MUNICIPALITY = ":" + FORM_PARAMETER_MUNICIPALITY;
	private static final String VIEW_PARAMETER_ZIP_CODE = ":" + FORM_PARAMETER_ZIP_CODE;

	private static final String VIEW_PARAMETER_BIRTHDAY = ":" + FORM_PARAMETER_BIRTHDAY;
	private static final String VIEW_PARAMETER_GWA = ":" + FORM_PARAMETER_GWA;
	private static final String VIEW_PARAMETER_CURRENTLY_EMPLOYED = ":" + FORM_PARAMETER_CURRENTLY_EMPLOYED;
	private static final String VIEW_PARAMETER_DATE_HIRED = ":" + FORM_PARAMETER_DATE_HIRED;

	private static final String VIEW_PARAMETER_CONTACTS_FORM = ":contactsForm";
	private static final String VIEW_PARAMETER_ROLES_FORM = ":rolesForm";

	private static final String VIEW_PARAMETER_QUERY_ROLE_ITEMS = ":queryRoleItems";
	private static final String VIEW_PARAMETER_QUERY_PERSON_LAST_NAME = ":" + QUERY_PARAMETER_PERSON_LAST_NAME;
	private static final String VIEW_PARAMETER_QUERY_ROLE_ID = ":" + QUERY_PARAMETER_ROLE_ID;
	private static final String VIEW_PARAMETER_QUERY_BIRTHDAY = ":" + QUERY_PARAMETER_BIRTHDAY;
	private static final String VIEW_PARAMETER_QUERY_ORDER_BY = ":" + QUERY_PARAMETER_ORDER_BY;
	private static final String VIEW_PARAMETER_QUERY_ORDER_TYPE = ":" + QUERY_PARAMETER_ORDER_TYPE;
	
	private static final String VIEW_PARAMETER_POST_QUERY_PARAMETERS = ":postQueryParameters";
	private static final String VIEW_PARAMETER_DATATABLE = ":dataTable";
	
	private static final String CREATE_SUCCESS_MESSAGE = "Successfully created the person ID [%d] \"%s\"!";
	private static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated the person ID [%d] \"%s\"!";
	private static final String DELETE_SUCCESS_MESSAGE = "Successfully deleted the person ID \"%d\"!";

	private PersonService personService;
	private RoleService roleService;

	@Override
	public void init() throws ServletException {
		VIEW_TEMPLATE = getServletContext().getRealPath("/") + "/person-registry.template.html";
		personService = new PersonService();
		roleService = new RoleService();
		HibernateUtility.initializeSessionFactory();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		TemplateEngine templateEngine = new TemplateEngine(response.getWriter());
		Map<String, Object> queryParameters = new LinkedHashMap<>();
		Map<String, Object> parameters = new LinkedHashMap<>();
		try {
			queryParameters = extractQueryParameters(request);
			parameters.put(VIEW_PARAMETER_MESSAGE, createMessage(request));
			String personId = request.getParameter(FORM_PARAMETER_PERSON_ID);
			if (personId == null) {
				parameters.put(VIEW_PARAMETER_HEADER, "Create new person");
				parameters.put(VIEW_PARAMETER_MODE, MODE_CREATE);
			}
			else {
				PersonDTO person = personService.get(NumberUtils.createInteger(personId));
				parameters.put(VIEW_PARAMETER_HEADER, "Update existing person");
				parameters.put(VIEW_PARAMETER_MODE, MODE_UPDATE);
				parameters.putAll(constructViewParametersFromPerson(person));
			}
		}
		catch (Exception cause) {
			parameters.put(VIEW_PARAMETER_MESSAGE, ExceptionHandler.printException(cause));
		}
		finally {
			parameters.put(VIEW_PARAMETER_DATATABLE, createDataTable(queryParameters, templateEngine));
			parameters.putAll(queryToViewParameters(request));
			templateEngine.render(VIEW_TEMPLATE, parameters);
		}
	}

	private Map<String, Object> extractQueryParameters(HttpServletRequest request) {
		Map<String, Object> queryParameters = new LinkedHashMap<>(5);
		queryParameters.put(QUERY_PARAMETER_PERSON_LAST_NAME, request.getParameter(QUERY_PARAMETER_PERSON_LAST_NAME));
		queryParameters.put(QUERY_PARAMETER_ROLE_ID, NumberUtils.createInteger(request.getParameter(QUERY_PARAMETER_ROLE_ID)));		
		queryParameters.put(QUERY_PARAMETER_BIRTHDAY, DateUtils.parse(request.getParameter(QUERY_PARAMETER_BIRTHDAY)));		
		queryParameters.put(QUERY_PARAMETER_ORDER_BY, request.getParameter(QUERY_PARAMETER_ORDER_BY));
		queryParameters.put(QUERY_PARAMETER_ORDER_TYPE, request.getParameter(QUERY_PARAMETER_ORDER_TYPE));
		return queryParameters;
	}

	private String createMessage(HttpServletRequest request) throws Exception {
		String message = null;
		String rawEncodedResponse = request.getParameter(FORM_PARAMETER_ENCODED_RESPONSE);
		if (rawEncodedResponse != null) {
			Integer encodedResponse = NumberUtils.createInteger(rawEncodedResponse);
			Integer personId = decodePersonId(encodedResponse);
			Integer mode = decodeMode(encodedResponse);
			PersonDTO person = null;
		
			if (mode.equals(MODE_CREATE)) {
				person = personService.get(personId);
				message = String.format(CREATE_SUCCESS_MESSAGE, person.getId(), person.getName());	
			}
			else if (mode.equals(MODE_UPDATE)) {
				person = personService.get(personId);
				message = String.format(UPDATE_SUCCESS_MESSAGE, person.getId(), person.getName());
			}
			else if (mode.equals(MODE_DELETE)) {
				message = String.format(DELETE_SUCCESS_MESSAGE, personId);
			}
		}
		return message;
	}

	private Integer decodePersonId(Integer encodedResponse) {
		return encodedResponse >> 8;
	}

	private Integer decodeMode(Integer encodedResponse) {
		return encodedResponse & 0xff;
	}

	private Map<String, Object> constructViewParametersFromPerson(PersonDTO person) {
		Map<String, Object> parameters = new LinkedHashMap<>(); 
		parameters.put(VIEW_PARAMETER_PERSON_ID, person.getId());
		parameters.put(VIEW_PARAMETER_TITLE, person.getName().getTitle());
		parameters.put(VIEW_PARAMETER_LAST_NAME, person.getName().getLastName());
		parameters.put(VIEW_PARAMETER_FIRST_NAME, person.getName().getFirstName());
		parameters.put(VIEW_PARAMETER_MIDDLE_NAME, person.getName().getMiddleName());
		parameters.put(VIEW_PARAMETER_SUFFIX, person.getName().getSuffix());

		parameters.put(VIEW_PARAMETER_STREET_NUMBER, person.getAddress().getStreetNumber());
		parameters.put(VIEW_PARAMETER_BARANGAY, person.getAddress().getBarangay());
		parameters.put(VIEW_PARAMETER_MUNICIPALITY, person.getAddress().getMunicipality());
		parameters.put(VIEW_PARAMETER_ZIP_CODE, person.getAddress().getZipCode());

		parameters.put(VIEW_PARAMETER_BIRTHDAY, toString(person.getBirthday()));
		parameters.put(VIEW_PARAMETER_GWA, person.getGWA());
		parameters.put(VIEW_PARAMETER_CURRENTLY_EMPLOYED, person.getCurrentlyEmployed());				
		if (person.getCurrentlyEmployed()) {
			parameters.put(VIEW_PARAMETER_DATE_HIRED, toString(person.getDateHired()));
		}

		StringBuilder assignedRoles = new StringBuilder();
		for (int i = 0; i < person.getRoles().size(); i++) {
			assignedRoles.append("<div><select class=\"assigned-roles\" name=\"personRoleIds\">:queryRoleItems</select><button onclick=\"this.parentNode.remove()\">Remove</button></div>");
		}

		assignedRoles.append("<script> var rolesId = [" + person.getRoles().stream().map(t -> t.getId().toString()).collect(Collectors.joining(",")) +  
			"]; document.querySelectorAll('select.assigned-roles').forEach((e, i) => e.value = rolesId[i]);</script>");
		parameters.put(VIEW_PARAMETER_ROLES_FORM, assignedRoles);

		// parameters.put(VIEW_PARAMETER_CONTACTS_FORM, person.getId());
		return parameters;
	} 

	private String toString(Object instance) {
		if (instance != null) {
			if (instance instanceof List) {
				return (String) ((List) instance).stream()
					.map(t -> t.toString())
					.collect(Collectors.joining("<br>"));
			}
			if (instance instanceof Date) {
				return DateUtils.toString((Date) instance);
			}
			return instance.toString();
		}
		return "";
	}

	private String createDataTable(Map<String, Object> queryParameters, TemplateEngine templateEngine) {
		String queryLastName = (String) queryParameters.get(QUERY_PARAMETER_PERSON_LAST_NAME);
		Integer queryRoleId = (Integer) queryParameters.get(QUERY_PARAMETER_ROLE_ID);
		Date queryBirthday = (Date) queryParameters.get(QUERY_PARAMETER_BIRTHDAY);
		String queryOrderBy = (String) queryParameters.get(QUERY_PARAMETER_ORDER_BY);
		String queryOrder = (String) queryParameters.get(QUERY_PARAMETER_ORDER_TYPE);

		List<String> headers = Arrays.asList("ID", "Name", "Address", "Birthday", "GWA", "Employment", "Contacts", "Roles");
		List<List<String>> data = personService.list(queryLastName, queryRoleId, queryBirthday, queryOrderBy, queryOrder).stream()
			.map(person -> Arrays.asList(toString(person.getId()), toString(person.getName()), toString(person.getAddress()), toString(person.getBirthday()), toString(person.getGWA()), person.getEmploymentStatus(), toString(person.getContacts()), toString(person.getRoles())))
			.collect(Collectors.toList());

		return templateEngine.renderTable(headers, data, SERVLET_PATH, TEMPLATE_POST_QUERY_PARAMETERS);		
	}

	private Map<String, Object> queryToViewParameters(HttpServletRequest request) {
		Map<String, Object> parameters = new LinkedHashMap<>(7);
		parameters.put(VIEW_PARAMETER_POST_QUERY_PARAMETERS, TEMPLATE_POST_QUERY_PARAMETERS);
		parameters.put(VIEW_PARAMETER_QUERY_PERSON_LAST_NAME, request.getParameter(QUERY_PARAMETER_PERSON_LAST_NAME));
		parameters.put(VIEW_PARAMETER_QUERY_ROLE_ID, request.getParameter(QUERY_PARAMETER_ROLE_ID));
		parameters.put(VIEW_PARAMETER_QUERY_BIRTHDAY, request.getParameter(QUERY_PARAMETER_BIRTHDAY));
		parameters.put(VIEW_PARAMETER_QUERY_ORDER_BY, request.getParameter(QUERY_PARAMETER_ORDER_BY));
		parameters.put(VIEW_PARAMETER_QUERY_ORDER_TYPE, request.getParameter(QUERY_PARAMETER_ORDER_TYPE));
		parameters.put(VIEW_PARAMETER_QUERY_ROLE_ITEMS, roleService.list().stream()
			.map(role -> String.format("<option value=\"%d\">%s</option>", role.getId(), role.getName()))
			.collect(Collectors.joining("")));

		return parameters;
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		TemplateEngine templateEngine = new TemplateEngine(response.getWriter());
		Map<String, Object> queryParameters = new LinkedHashMap<>();
		Map<String, Object> parameters = new LinkedHashMap<>();

		PersonDTO person = createPersonFromRequest(request);
		String mode = request.getParameter(FORM_PARAMETER_MODE);

		try {
			queryParameters = extractQueryParameters(request);
			if (mode.equals(MODE_CREATE.toString())) {
				parameters.put(VIEW_PARAMETER_HEADER, "Create new person");
				personService.validate(person);
				person.setId((Integer) personService.create(person));
				response.sendRedirect(String.format("%s?%s=%d", SERVLET_PATH, FORM_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_CREATE, person.getId())));
			}
			else if (mode.equals(MODE_UPDATE.toString())){
				parameters.put(VIEW_PARAMETER_HEADER, "Update existing person");
				personService.get(person.getId());
				personService.validate(person);
				personService.update(person);
				response.sendRedirect(String.format("%s?%s=%d", SERVLET_PATH, FORM_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_UPDATE, person.getId())));
			}	
			else if (mode.equals(MODE_DELETE.toString())) {
				personService.delete(person.getId());	
				response.sendRedirect(String.format("%s?%s=%d", SERVLET_PATH, FORM_PARAMETER_ENCODED_RESPONSE, encodeResponse(MODE_DELETE, person.getId())));
			}
		}
		catch (Exception cause) {
			if (cause instanceof ValidationException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				parameters.put(VIEW_PARAMETER_MODE, mode);
			}
			else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				parameters.put(VIEW_PARAMETER_HEADER, "Create new person");
				parameters.put(VIEW_PARAMETER_MODE, MODE_CREATE);
			}
			parameters.put(VIEW_PARAMETER_MESSAGE, ExceptionHandler.printException(cause));
			parameters.putAll(constructViewParametersFromPerson(person));
			parameters.put(VIEW_PARAMETER_DATATABLE, createDataTable(queryParameters, templateEngine));
			parameters.putAll(queryToViewParameters(request));
			templateEngine.render(VIEW_TEMPLATE, parameters);
		}
	}

	private PersonDTO createPersonFromRequest(HttpServletRequest request) {
		PersonDTO person = new PersonDTO();
		person.setId(NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_PERSON_ID)));		
		
		NameDTO name = new NameDTO();		
		name.setTitle(StringUtils.trim(request.getParameter(FORM_PARAMETER_TITLE)));
		name.setLastName(StringUtils.trim(request.getParameter(FORM_PARAMETER_LAST_NAME)));
		name.setFirstName(StringUtils.trim(request.getParameter(FORM_PARAMETER_FIRST_NAME)));
		name.setMiddleName(StringUtils.trim(request.getParameter(FORM_PARAMETER_MIDDLE_NAME)));
		name.setSuffix(StringUtils.trim(request.getParameter(FORM_PARAMETER_SUFFIX)));
		person.setName(name);

		AddressDTO address = new AddressDTO();
		address.setStreetNumber(StringUtils.trim(request.getParameter(FORM_PARAMETER_STREET_NUMBER)));
		address.setBarangay(NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_BARANGAY)));
		address.setMunicipality(StringUtils.trim(request.getParameter(FORM_PARAMETER_MUNICIPALITY)));
		address.setZipCode(NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_ZIP_CODE)));
		person.setAddress(address);

		person.setBirthday(DateUtils.parse(request.getParameter(FORM_PARAMETER_BIRTHDAY)));
		person.setGWA(NumberUtils.createBigDecimal(request.getParameter(FORM_PARAMETER_GWA)));

		person.setCurrentlyEmployed("Yes".equals(request.getParameter(FORM_PARAMETER_CURRENTLY_EMPLOYED)));
		if (person.getCurrentlyEmployed()) {
			person.setDateHired(DateUtils.parse(request.getParameter(FORM_PARAMETER_DATE_HIRED)));
		}

		for (String roleId: request.getParameterValues(FORM_PARAMETER_PERSON_ROLE_IDS)) {
			person.getRoles().add(new RoleDTO(NumberUtils.createInteger(roleId)));			
		}

		return person;
	}
	
	private Integer encodeResponse(Integer mode, Integer personId) {
		return mode & 0xff | personId << 8;
	}

	@Override
	public void destroy() {
		HibernateUtility.closeSessionFactory();
	}
}