package com.ecc.spring_xml.controller;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.dao.DataRetrievalFailureException;

import org.apache.commons.lang3.StringUtils;

import com.ecc.spring_xml.dto.AddressDTO;
import com.ecc.spring_xml.dto.ContactDTO;
import com.ecc.spring_xml.dto.NameDTO;
import com.ecc.spring_xml.dto.PersonDTO;
import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.service.PersonService;
import com.ecc.spring_xml.service.RoleService;
import com.ecc.spring_xml.util.app.DateUtils;
import com.ecc.spring_xml.util.app.NumberUtils;
import com.ecc.spring_xml.util.validator.ValidationException;

public class PersonController extends MultiActionController {
	private static final String CREATE_SUCCESS_MESSAGE = "Successfully created the person \"%s\"!";
	private static final String UPDATE_SUCCESS_MESSAGE = "Successfully updated the person \"%s\"!";
	private static final String DELETE_SUCCESS_MESSAGE = "Successfully deleted the person \"%s\"!";

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

	private static final String FORM_PARAMETER_PERSON_CONTACT_TYPE = "contactType";
	private static final String FORM_PARAMETER_PERSON_CONTACT_DATA = "contactData";

	private static final String QUERY_PARAMETER_SEARCH_TYPE = "querySearchType";
	private static final String QUERY_PARAMETER_PERSON_LAST_NAME = "queryLastName";
	private static final String QUERY_PARAMETER_ROLE_ID = "queryRoleId";
	private static final String QUERY_PARAMETER_BIRTHDAY = "queryBirthday";
	private static final String QUERY_PARAMETER_ORDER_BY = "queryOrderBy";
	private static final String QUERY_PARAMETER_ORDER_TYPE = "queryOrderType";

	private static final String VIEW_PARAMETER_ROLE_ITEMS = "roleItems";
	private static final String VIEW_PARAMETER_ERROR_MESSAGE = "errorMessage";
	private static final String VIEW_PARAMETER_SUCCESS_MESSAGE = "successMessage";
	private static final String VIEW_PARAMETER_ACTION = "action";
	private static final String VIEW_PARAMETER_HEADER = "headerTitle";
	private static final String VIEW_PARAMETER_DATA = "data";
	private static final String VIEW_PARAMETER_ASSIGNED_CONTACTS = "assignedContacts";
	private static final String VIEW_PARAMETER_ASSIGNED_CONTACT_TYPES = "assignedContactTypes";
	private static final String VIEW_PARAMETER_ASSIGNED_ROLE_IDS = "assignedRoleIds";

	private static final String ATTRIBUTE_IS_ACTION_DELETE = "isActionDelete";
	private static final String ATTRIBUTE_PERSON_NOT_FOUND = "personNotFound";

	private PersonService personService;
	private RoleService roleService;

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("person");

		Integer personId = NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_PERSON_ID));
		if (request.getAttribute(ATTRIBUTE_IS_ACTION_DELETE) != null || 
			request.getAttribute(ATTRIBUTE_PERSON_NOT_FOUND) != null || personId == null) {
			modelView.addObject(VIEW_PARAMETER_HEADER, "Create new person");
			modelView.addObject(VIEW_PARAMETER_ACTION, "/create");
		}
		else {
			PersonDTO person = personService.get(personId);
			modelView.addAllObjects(constructViewParametersFromPerson(person));
			modelView.addObject(VIEW_PARAMETER_HEADER, "Update existing person");	
			modelView.addObject(VIEW_PARAMETER_ACTION, "/update");
		}

		modelView.addAllObjects(queryToViewParameters(request));
		modelView.addObject(VIEW_PARAMETER_ROLE_ITEMS, roleService.list());
		modelView.addObject(VIEW_PARAMETER_DATA, personService.list(
			request.getParameter(QUERY_PARAMETER_PERSON_LAST_NAME),
			NumberUtils.createInteger(request.getParameter(QUERY_PARAMETER_ROLE_ID)),	
			DateUtils.parse(request.getParameter(QUERY_PARAMETER_BIRTHDAY)),
			request.getParameter(QUERY_PARAMETER_ORDER_BY),
			request.getParameter(QUERY_PARAMETER_ORDER_TYPE)));

		if (RequestContextUtils.getInputFlashMap(request) != null) {
			modelView.addObject(VIEW_PARAMETER_SUCCESS_MESSAGE, RequestContextUtils.getInputFlashMap(request).get(VIEW_PARAMETER_SUCCESS_MESSAGE));
		}
		return modelView;
	}

	public String create(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equals("POST")) {
			PersonDTO person = createPersonFromRequest(request);
			personService.validate(person);
			personService.create(person);

			String message = String.format(CREATE_SUCCESS_MESSAGE, person.getName());
			RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
			return "redirect:/person/list";
		}
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	public String update(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equals("POST")) {
			PersonDTO person = createPersonFromRequest(request);
			Integer personId = NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_PERSON_ID));
			personService.get(personId);
			personService.validate(person);
			personService.update(person);

			String message = String.format(UPDATE_SUCCESS_MESSAGE, person.getName());
			RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
			return "redirect:/person/list";
		}
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	public String delete(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equals("POST")) {
			request.setAttribute(ATTRIBUTE_IS_ACTION_DELETE, true);
			Integer personId = NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_PERSON_ID));
			PersonDTO person = personService.get(personId);
			personService.delete(personId);	

			String message = String.format(DELETE_SUCCESS_MESSAGE, person.getName());
			RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
			return "redirect:/person/list";
		}
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception cause) {
		ModelAndView modelView = list(request, response);
		if (cause instanceof ValidationException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			modelView.addAllObjects(constructViewParametersFromPerson(createPersonFromRequest(request)));
		}
		else if (cause instanceof DataRetrievalFailureException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute(ATTRIBUTE_PERSON_NOT_FOUND, true);
		}
		else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		modelView.addObject(VIEW_PARAMETER_ERROR_MESSAGE, "Error: " + cause.getMessage());
		return modelView;
	}

	private Map<String, Object> queryToViewParameters(HttpServletRequest request) {
		Map<String, Object> parameters = new HashMap<>(6);
		parameters.put(QUERY_PARAMETER_SEARCH_TYPE, request.getParameter(QUERY_PARAMETER_SEARCH_TYPE));
		parameters.put(QUERY_PARAMETER_PERSON_LAST_NAME, request.getParameter(QUERY_PARAMETER_PERSON_LAST_NAME));
		parameters.put(QUERY_PARAMETER_ROLE_ID, request.getParameter(QUERY_PARAMETER_ROLE_ID));
		parameters.put(QUERY_PARAMETER_BIRTHDAY, request.getParameter(QUERY_PARAMETER_BIRTHDAY));
		parameters.put(QUERY_PARAMETER_ORDER_BY, request.getParameter(QUERY_PARAMETER_ORDER_BY));
		parameters.put(QUERY_PARAMETER_ORDER_TYPE, request.getParameter(QUERY_PARAMETER_ORDER_TYPE));
		return parameters;
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
		address.setBarangay(request.getParameter(FORM_PARAMETER_BARANGAY));
		address.setMunicipality(StringUtils.trim(request.getParameter(FORM_PARAMETER_MUNICIPALITY)));
		address.setZipCode(NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_ZIP_CODE)));
		person.setAddress(address);

		person.setBirthday(DateUtils.parse(request.getParameter(FORM_PARAMETER_BIRTHDAY)));
		person.setGWA(NumberUtils.createBigDecimal(request.getParameter(FORM_PARAMETER_GWA)));

		person.setCurrentlyEmployed("Yes".equals(request.getParameter(FORM_PARAMETER_CURRENTLY_EMPLOYED)));
		if (person.getCurrentlyEmployed()) {
			person.setDateHired(DateUtils.parse(request.getParameter(FORM_PARAMETER_DATE_HIRED)));
		}

		String[] roleIds = request.getParameterValues(FORM_PARAMETER_PERSON_ROLE_IDS);
		if (roleIds != null) {
			for (String roleId: request.getParameterValues(FORM_PARAMETER_PERSON_ROLE_IDS)) {
				person.getRoles().add(new RoleDTO(NumberUtils.createInteger(roleId), roleId));
			}			
		}

		String[] contactTypes = request.getParameterValues(FORM_PARAMETER_PERSON_CONTACT_TYPE);
		String[] contactData = request.getParameterValues(FORM_PARAMETER_PERSON_CONTACT_DATA);

		if (contactData != null && contactTypes != null) {
			for (int i = 0; i < contactTypes.length; i++) {
				person.getContacts().add(new ContactDTO(contactTypes[i], contactData[i]));			
			}			
		}
		return person;
	}

	private Map<String, Object> constructViewParametersFromPerson(PersonDTO person) {
		Map<String, Object> parameters = new HashMap<>(); 
		parameters.put(FORM_PARAMETER_PERSON_ID, person.getId());
		parameters.put(FORM_PARAMETER_TITLE, person.getName().getTitle());
		parameters.put(FORM_PARAMETER_LAST_NAME, person.getName().getLastName());
		parameters.put(FORM_PARAMETER_FIRST_NAME, person.getName().getFirstName());
		parameters.put(FORM_PARAMETER_MIDDLE_NAME, person.getName().getMiddleName());
		parameters.put(FORM_PARAMETER_SUFFIX, person.getName().getSuffix());

		parameters.put(FORM_PARAMETER_STREET_NUMBER, person.getAddress().getStreetNumber());
		parameters.put(FORM_PARAMETER_BARANGAY, person.getAddress().getBarangay());
		parameters.put(FORM_PARAMETER_MUNICIPALITY, person.getAddress().getMunicipality());
		parameters.put(FORM_PARAMETER_ZIP_CODE, person.getAddress().getZipCode());

		parameters.put(FORM_PARAMETER_BIRTHDAY, person.getBirthday());
		parameters.put(FORM_PARAMETER_GWA, person.getGWA());
		parameters.put(FORM_PARAMETER_CURRENTLY_EMPLOYED, person.getCurrentlyEmployed());				
		if (person.getCurrentlyEmployed()) {
			parameters.put(FORM_PARAMETER_DATE_HIRED, person.getDateHired());
		}

		parameters.put(VIEW_PARAMETER_ASSIGNED_CONTACTS, person.getContacts());

		parameters.put(VIEW_PARAMETER_ASSIGNED_ROLE_IDS, person.getRoles().stream().map(t -> t.getId().toString()).collect(Collectors.joining(",")));
		parameters.put(VIEW_PARAMETER_ASSIGNED_CONTACT_TYPES, person.getContacts().stream().map(t -> "'" + t.getContactType() + "'").collect(Collectors.joining(",")));
		return parameters;
	} 
}