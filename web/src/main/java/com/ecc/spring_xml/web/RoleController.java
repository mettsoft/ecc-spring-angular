package com.ecc.spring_xml.web;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.service.RoleService;
import com.ecc.spring_xml.util.NumberUtils;
import com.ecc.spring_xml.util.ValidationUtils;
import com.ecc.spring_xml.util.ValidationException;

public class RoleController extends MultiActionController {
	private static final String QUERY_PARAMETER_ROLE_ID = "id";

	private static final String VIEW_PARAMETER_ERROR_MESSAGES = "errorMessages";
	private static final String VIEW_PARAMETER_SUCCESS_MESSAGE = "successMessage";
	private static final String VIEW_PARAMETER_HEADER = "headerTitle";
	private static final String VIEW_PARAMETER_ACTION = "action";
	private static final String VIEW_PARAMETER_DATA = "data";
	private static final String VIEW_PARAMETER_LOCALE = "locale";

	private static final String ATTRIBUTE_FORCE_CREATE_MODE = "isCreateMode";

	@Autowired
	private RoleService roleService;

	@Autowired
	private MessageSource messageSource;

	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelView = new ModelAndView("role");
		Locale locale = RequestContextUtils.getLocale(request);
		modelView.addObject(VIEW_PARAMETER_LOCALE, locale);

		RoleDTO role = new RoleDTO();
		Integer roleId = NumberUtils.createInteger(request.getParameter(QUERY_PARAMETER_ROLE_ID));
		if (request.getAttribute(ATTRIBUTE_FORCE_CREATE_MODE) != null || roleId == null) {
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("role.headerTitle.create", null, locale));
			modelView.addObject(VIEW_PARAMETER_ACTION, "/create");
		}
		else {
			try {
				role = roleService.get(roleId);
			}
			catch (ValidationException cause) {
				request.setAttribute(ATTRIBUTE_FORCE_CREATE_MODE, true);
				throw cause;
			}
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("role.headerTitle.update", null, locale));	
			modelView.addObject(VIEW_PARAMETER_ACTION, "/update");
		}
		modelView.addObject(DEFAULT_COMMAND_NAME, role);

		if (RequestContextUtils.getInputFlashMap(request) != null) {
			modelView.addObject(VIEW_PARAMETER_SUCCESS_MESSAGE, RequestContextUtils.getInputFlashMap(request).get(VIEW_PARAMETER_SUCCESS_MESSAGE));
		}
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

			role = roleService.get(role.getId());
			String message = messageSource.getMessage("role.successMessage.update", new Object[] {role.getName()}, locale);
			if (role.getPersons().size() > 0) {
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
			request.setAttribute(ATTRIBUTE_FORCE_CREATE_MODE, true);
			roleService.delete(role.getId());	

			String message = messageSource.getMessage("role.successMessage.delete", new Object[] {role.getName()}, locale);
			RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
			return "redirect:/role/list";
		}
		throw new UnsupportedOperationException("Unsupported operation!");
	}

	public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception cause) throws Exception {
		if (cause instanceof ServletRequestBindingException || cause instanceof ValidationException) {
			Locale locale = RequestContextUtils.getLocale(request);
			ModelAndView modelView = list(request, response);
			List<ObjectError> errors = null;
			Object target = null;

			if (cause instanceof ServletRequestBindingException) {
			    BindException bindException = (BindException) ((ServletRequestBindingException)cause).getRootCause();
			    errors = bindException.getAllErrors();
			    target = bindException.getTarget();
			}
			else {
				ValidationException validationException = (ValidationException) cause;
				errors = validationException.getAllErrors();
				target = validationException.getTarget();
			}

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			if (request.getAttribute(ATTRIBUTE_FORCE_CREATE_MODE) == null) {
				modelView.addObject(DEFAULT_COMMAND_NAME, target);				
			}
			modelView.addObject(VIEW_PARAMETER_ERROR_MESSAGES, ValidationUtils.localize(errors, messageSource, locale));
			cause.printStackTrace();
			if (cause.getCause() != null) {
				cause.printStackTrace();
			}

			return modelView;
		}
		throw cause;
	}
}