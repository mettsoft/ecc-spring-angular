package com.ecc.spring_xml.web;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.validation.BindException;

import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.service.RoleService;
import com.ecc.spring_xml.util.app.NumberUtils;

public class RoleController extends MultiActionController {
	private static final String QUERY_PARAMETER_ROLE_ID = "id";

	private static final String VIEW_PARAMETER_ERROR_MESSAGE = "errorMessage";
	private static final String VIEW_PARAMETER_SUCCESS_MESSAGE = "successMessage";
	private static final String VIEW_PARAMETER_HEADER = "headerTitle";
	private static final String VIEW_PARAMETER_ACTION = "action";
	private static final String VIEW_PARAMETER_DATA = "data";
	private static final String VIEW_PARAMETER_LOCALE = "locale";

	private static final String ATTRIBUTE_IS_ACTION_DELETE = "isActionDelete";
	private static final String ATTRIBUTE_ROLE_NOT_FOUND = "roleNotFound";

	private RoleService roleService;
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("role");
		Locale locale = RequestContextUtils.getLocale(request);
		modelView.addObject(VIEW_PARAMETER_LOCALE, locale);

		RoleDTO role = new RoleDTO();
		Integer roleId = NumberUtils.createInteger(request.getParameter(QUERY_PARAMETER_ROLE_ID));
		if (request.getAttribute(ATTRIBUTE_IS_ACTION_DELETE) != null || 
			request.getAttribute(ATTRIBUTE_ROLE_NOT_FOUND) != null || roleId == null) {
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("role.headerTitle.create", null, locale));
			modelView.addObject(VIEW_PARAMETER_ACTION, "/create");
		}
		else {
			role = roleService.get(roleId);
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("role.headerTitle.update", null, locale));	
			modelView.addObject(VIEW_PARAMETER_ACTION, "/update");
		}
		modelView.addObject(DEFAULT_COMMAND_NAME, role);
		if (RequestContextUtils.getInputFlashMap(request) != null)
			modelView.addObject(VIEW_PARAMETER_SUCCESS_MESSAGE, RequestContextUtils.getInputFlashMap(request).get(VIEW_PARAMETER_SUCCESS_MESSAGE));
		modelView.addObject(VIEW_PARAMETER_DATA, roleService.list());
		return modelView;
	}

	public String create(HttpServletRequest request, HttpServletResponse response, RoleDTO role) {
		Locale locale = RequestContextUtils.getLocale(request);
		if (request.getMethod().equals("POST")) {
			roleService.create(role);

			String message = messageSource.getMessage("role.successMessage.create", new Object[] {role.getName()}, locale);
			RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
			return "redirect:/role/list";
		}
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	public String update(HttpServletRequest request, HttpServletResponse response, RoleDTO role) {
		Locale locale = RequestContextUtils.getLocale(request);
		if (request.getMethod().equals("POST")) {
			roleService.update(role);

			String message = messageSource.getMessage("role.successMessage.update", new Object[] {role.getName()}, locale);
			if (roleService.get(role.getId()).getPersons().size() > 0) {
				String personNames = role.getPersons().stream()
					.map(person -> person.getName().toString())
					.collect(Collectors.joining("; "));
				message += " " + messageSource.getMessage("role.successMessage.affectedPersons", new Object[] {personNames}, locale);
			}
			RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
			return "redirect:/role/list";
		}
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	public String delete(HttpServletRequest request, HttpServletResponse response, RoleDTO role) {
		Locale locale = RequestContextUtils.getLocale(request);
		if (request.getMethod().equals("POST")) {
			request.setAttribute(ATTRIBUTE_IS_ACTION_DELETE, true);
			roleService.delete(role.getId());	

			String message = messageSource.getMessage("role.successMessage.delete", new Object[] {role.getName()}, locale);
			RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
			return "redirect:/role/list";
		}
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception cause) {
		Locale locale = RequestContextUtils.getLocale(request);
		ModelAndView modelView = list(request, response);

		if (cause instanceof ServletRequestBindingException) {
		    BindException bindException = (BindException) ((ServletRequestBindingException)cause).getRootCause();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			modelView.addObject(DEFAULT_COMMAND_NAME, bindException.getTarget());

			String errorMessage = bindException.getAllErrors().stream()
				.map(t -> messageSource.getMessage
					(
						t.getCode(), 
						Arrays.stream(t.getArguments())
							.map(u -> u.toString().startsWith("localize:")? 
								messageSource.getMessage
								(
									StringUtils.substringAfter(u.toString(), "localize:"), 
									null, 
									locale
								): u)
							.toArray(),
						locale))
				.collect(Collectors.joining("<br />"));
		    modelView.addObject(VIEW_PARAMETER_ERROR_MESSAGE, errorMessage);
		}
		else if (cause instanceof DataRetrievalFailureException) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			request.setAttribute(ATTRIBUTE_ROLE_NOT_FOUND, true);
		}
		else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		// TODO
		modelView.addObject(VIEW_PARAMETER_ERROR_MESSAGE, "Error: " + cause.getMessage());
		return modelView;
	}
}