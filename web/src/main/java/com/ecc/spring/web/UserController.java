package com.ecc.spring.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;

import com.ecc.spring.dto.UserDTO;
import com.ecc.spring.service.UserService;
import com.ecc.spring.util.ValidationException;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userService);
	}

	@GetMapping
	public List<UserDTO> list() {
		return userService.list();
	}

	@GetMapping("/{id}")
	public UserDTO get(@PathVariable Integer id) {
		try {
			return userService.get(id);
		}
		catch (DataRetrievalFailureException cause) {
			throw new ValidationException("user.validation.message.notFound", new UserDTO(), id);		
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public void create(@Validated UserDTO user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), user);
		}
		try {
			userService.create(user);		
		}
		catch(DataIntegrityViolationException cause) {
			throw new ValidationException("user.validation.message.duplicateEntry", user, user.getUsername());
		}
	}

	@PutMapping
	public void update(@Validated UserDTO user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), user);
		}	
		try {
			userService.update(user);
		}
		catch(DataIntegrityViolationException cause) {
			throw new ValidationException("user.validation.message.duplicateEntry", user, user.getUsername());
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		userService.delete(id);	
	}
}