package com.ecc.spring.web;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ecc.spring.dto.UserDTO;
import com.ecc.spring.service.UserService;
import com.ecc.spring.util.NumberUtils;
import com.ecc.spring.util.ValidationUtils;
import com.ecc.spring.util.ValidationException;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final String DEFAULT_COMMAND_NAME = "command";
	private static final String QUERY_PARAMETER_ROLE_ID = "id";

	private static final String VIEW_PARAMETER_ERROR_MESSAGES = "errorMessages";
	private static final String VIEW_PARAMETER_SUCCESS_MESSAGE = "successMessage";
	private static final String VIEW_PARAMETER_HEADER = "headerTitle";
	private static final String VIEW_PARAMETER_ACTION = "action";
	private static final String VIEW_PARAMETER_DATA = "data";

	private static final String ATTRIBUTE_FORCE_CREATE_MODE = "isCreateMode";

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userService);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, Locale locale) {
		ModelAndView modelView = new ModelAndView("user");

		UserDTO user = new UserDTO();
		Integer userId = NumberUtils.createInteger(request.getParameter(QUERY_PARAMETER_ROLE_ID));
		if (request.getAttribute(ATTRIBUTE_FORCE_CREATE_MODE) != null || userId == null) {
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("user.headerTitle.create", null, locale));
			modelView.addObject(VIEW_PARAMETER_ACTION, "/create");
		}
		else {
			try {
				user = userService.get(userId);
			}
			catch (DataRetrievalFailureException cause) {
				request.setAttribute(ATTRIBUTE_FORCE_CREATE_MODE, true);
				throw new ValidationException("user.validation.message.notFound", new UserDTO(), userId);		
			}
			modelView.addObject(VIEW_PARAMETER_HEADER, messageSource.getMessage("user.headerTitle.update", null, locale));
			modelView.addObject(VIEW_PARAMETER_ACTION, "/update");
		}
		modelView.addObject(DEFAULT_COMMAND_NAME, user);

		if (RequestContextUtils.getInputFlashMap(request) != null) {
			modelView.addObject(VIEW_PARAMETER_SUCCESS_MESSAGE, RequestContextUtils.getInputFlashMap(request).get(VIEW_PARAMETER_SUCCESS_MESSAGE));
		}
		modelView.addObject(VIEW_PARAMETER_DATA, userService.list());
		return modelView;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(HttpServletRequest request, @Validated UserDTO user, BindingResult bindingResult, Locale locale) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), user);
		}

		try {
			userService.create(user);		
		}
		catch(DataIntegrityViolationException cause) {
			throw new ValidationException("user.validation.message.duplicateEntry", user, user.getUsername());
		}

		String message = messageSource.getMessage("user.successMessage.create", new Object[] {user.getUsername()}, locale);
		RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
		return "redirect:/users";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, @Validated UserDTO user, BindingResult bindingResult, Locale locale) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), user);
		}
	
		try {
			userService.update(user);
		}
		catch(DataIntegrityViolationException cause) {
			throw new ValidationException("user.validation.message.duplicateEntry", user, user.getUsername());
		}

		String message = messageSource.getMessage("user.successMessage.update", new Object[] { user.getUsername() }, locale);
		RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
		return "redirect:/users";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request, UserDTO user, Locale locale) {
		request.setAttribute(ATTRIBUTE_FORCE_CREATE_MODE, true);
		userService.delete(user.getId());	

		String message = messageSource.getMessage("user.successMessage.delete", new Object[] { user.getUsername() }, locale);
		RequestContextUtils.getOutputFlashMap(request).put(VIEW_PARAMETER_SUCCESS_MESSAGE, message);
		return "redirect:/users";
	}

  @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ValidationException.class })
	public ModelAndView exceptionHandler(HttpServletRequest request, ValidationException cause, Locale locale) {
		ModelAndView modelView = list(request, locale);
		List<ObjectError> errors = cause.getAllErrors();
		Object target = cause.getTarget();
	
		if (request.getAttribute(ATTRIBUTE_FORCE_CREATE_MODE) == null) {
			modelView.addObject(DEFAULT_COMMAND_NAME, target);				
		}
		modelView.addObject(VIEW_PARAMETER_ERROR_MESSAGES, ValidationUtils.localize(errors, messageSource, locale));

		for (String message : ValidationUtils.localize(errors, messageSource, Locale.ENGLISH)) {
			logger.info(message, cause);
		}
		return modelView;
	}
}