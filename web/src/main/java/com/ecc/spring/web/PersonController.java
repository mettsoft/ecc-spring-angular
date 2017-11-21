package com.ecc.spring.web;

import java.util.Locale;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ecc.spring.dto.PersonDTO;
import com.ecc.spring.service.PersonService;
import com.ecc.spring.service.RoleService;
import com.ecc.spring.util.DateUtils;
import com.ecc.spring.util.NumberUtils;
import com.ecc.spring.util.ValidationUtils;
import com.ecc.spring.util.ValidationException;

@Controller
@RequestMapping("/persons")
public class PersonController {
	private static final String DEFAULT_COMMAND_NAME = "command";
	private static final String FORM_PARAMETER_PERSON_ID = "id";

	private static final String FORM_PARAMETER_PERSON_ROLE_IDS = "personRoleIds";

	private static final String FORM_PARAMETER_PERSON_CONTACT_TYPE = "contactType";
	private static final String FORM_PARAMETER_PERSON_CONTACT_DATA = "contactData";

	private static final String QUERY_PARAMETER_PERSON_LAST_NAME = "queryLastName";
	private static final String QUERY_PARAMETER_ROLE_ID = "queryRoleId";
	private static final String QUERY_PARAMETER_BIRTHDAY = "queryBirthday";
	private static final String QUERY_PARAMETER_ORDER_BY = "queryOrderBy";
	private static final String QUERY_PARAMETER_ORDER_TYPE = "queryOrderType";

	private static final String VIEW_PARAMETER_ROLE_ITEMS = "roleItems";
	private static final String VIEW_PARAMETER_ERROR_MESSAGES = "errorMessages";
	private static final String VIEW_PARAMETER_SUCCESS_MESSAGE = "successMessage";
	private static final String VIEW_PARAMETER_ACTION = "action";
	private static final String VIEW_PARAMETER_HEADER = "headerTitle";
	private static final String VIEW_PARAMETER_DATA = "data";
	private static final String VIEW_PARAMETER_ASSIGNED_CONTACT_TYPES = "assignedContactTypes";
	private static final String VIEW_PARAMETER_ASSIGNED_ROLE_IDS = "assignedRoleIds";

	private static final String ATTRIBUTE_FORCE_CREATE_MODE = "isCreateMode";

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MessageSource messageSource;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && personService.supports(binder.getTarget().getClass())) {
			binder.setValidator(personService);		
		}
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtils.DATE_FORMAT, true));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, Locale locale) {
		ModelAndView modelView = new ModelAndView("person");

		PersonDTO person = new PersonDTO();
		Integer personId = NumberUtils.createInteger(request.getParameter(FORM_PARAMETER_PERSON_ID));
		if (request.getAttribute(ATTRIBUTE_FORCE_CREATE_MODE) != null || personId == null) {
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("person.headerTitle.create", null, locale));
			modelView.addObject(VIEW_PARAMETER_ACTION, "/create");
		}
		else {
			try {
				person = personService.get(personId);
			}
			catch (DataRetrievalFailureException cause) {
				request.setAttribute(ATTRIBUTE_FORCE_CREATE_MODE, true);
				throw new ValidationException("person.validation.message.notFound", new PersonDTO(), personId);
			}
			modelView.addAllObjects(constructViewParametersFromPerson(person));
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("person.headerTitle.update", null, locale));
			modelView.addObject(VIEW_PARAMETER_ACTION, "/update");
		}
		modelView.addObject(DEFAULT_COMMAND_NAME, person);
		modelView.addObject(VIEW_PARAMETER_ROLE_ITEMS, roleService.list());

		if (RequestContextUtils.getInputFlashMap(request) != null) {
			modelView.addObject(VIEW_PARAMETER_SUCCESS_MESSAGE, RequestContextUtils.getInputFlashMap(request).get(VIEW_PARAMETER_SUCCESS_MESSAGE));
		}

		modelView.addObject(VIEW_PARAMETER_DATA, personService.list(
			request.getParameter(QUERY_PARAMETER_PERSON_LAST_NAME),
			NumberUtils.createInteger(request.getParameter(QUERY_PARAMETER_ROLE_ID)),	
			DateUtils.parse(request.getParameter(QUERY_PARAMETER_BIRTHDAY)),
			request.getParameter(QUERY_PARAMETER_ORDER_BY),
			request.getParameter(QUERY_PARAMETER_ORDER_TYPE)));
		return modelView;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(HttpServletRequest request, @Validated PersonDTO person, BindingResult bindingResult, Locale locale) {
		if (bindingResult != null && bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), person);
		}

		personService.create(person);

		String message = messageSource.getMessage("person.successMessage.create", new Object[] { person.getName() }, locale);
		RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
		return "redirect:/persons";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, Locale locale) {
		request.setAttribute(ATTRIBUTE_FORCE_CREATE_MODE, true);
		PersonDTO person = personService.createPersonDTO(file);
		personService.validate(person, DEFAULT_COMMAND_NAME);
		return create(request, person, null, locale);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, @Validated PersonDTO person, BindingResult bindingResult, Locale locale) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), person);
		}
		personService.update(person);

		String message = messageSource.getMessage("person.successMessage.update", new Object[] { person.getName() }, locale);
		RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
		return "redirect:/persons";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, PersonDTO person, Locale locale) {
		request.setAttribute(ATTRIBUTE_FORCE_CREATE_MODE, true);
		personService.delete(person.getId());

		String message = messageSource.getMessage("person.successMessage.delete", new Object[] { person.getName() }, locale);
		RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
		return "redirect:/persons";
	}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ValidationException.class })
	public ModelAndView exceptionHandler(HttpServletRequest request, ValidationException cause, Locale locale) {
		ModelAndView modelView = list(request, locale);
		List<ObjectError> errors = cause.getAllErrors();
		PersonDTO person = (PersonDTO) cause.getTarget();

		if (request.getAttribute(ATTRIBUTE_FORCE_CREATE_MODE) == null) {
			modelView.addAllObjects(constructViewParametersFromPerson(person));
			modelView.addObject(DEFAULT_COMMAND_NAME, person);
		}
		modelView.addObject(VIEW_PARAMETER_ERROR_MESSAGES, ValidationUtils.localize(errors, messageSource, locale));

		for (String message : ValidationUtils.localize(errors, messageSource, Locale.ENGLISH)) {
			logger.info(message, cause);
		}
		return modelView;
	}

	private Map<String, Object> constructViewParametersFromPerson(PersonDTO person) {
		Map<String, Object> parameters = new HashMap<>(2); 
		parameters.put(VIEW_PARAMETER_ASSIGNED_ROLE_IDS, person.getRoles().stream().map(t -> t.getId().toString()).collect(Collectors.joining(",")));
		parameters.put(VIEW_PARAMETER_ASSIGNED_CONTACT_TYPES, person.getContacts().stream().map(t -> "'" + t.getContactType() + "'").collect(Collectors.joining(",")));
		return parameters;
	} 
}