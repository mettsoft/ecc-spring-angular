package com.ecc.spring.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;

import com.ecc.spring.dto.PersonDTO;
import com.ecc.spring.service.PersonService;
import com.ecc.spring.util.DateUtils;
import com.ecc.spring.util.ValidationException;

@RestController
@RequestMapping("/persons")
public class PersonController {
	private static final String DEFAULT_COMMAND_NAME = "command";

	@Autowired
	private PersonService personService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && personService.supports(binder.getTarget().getClass())) {
			binder.setValidator(personService);		
		}
    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"), true));
	}

	@GetMapping
	public List<PersonDTO> list(
		@RequestParam(value = "lastName", required = false) String lastName,
		@RequestParam(value = "roleId", required = false) Integer roleId,
		@RequestParam(value = "birthday", required = false) Date birthday,
		@RequestParam(value = "orderBy", required = false) String orderBy,
		@RequestParam(value = "orderType", required = false) String orderType) {
		return personService.list(lastName, roleId, birthday, orderBy, orderType);
	}

	@GetMapping("/{id}")
	public PersonDTO get(@PathVariable Integer id) {
		try {
			return personService.get(id);
		}
		catch (DataRetrievalFailureException cause) {
			throw new ValidationException("person.validation.message.notFound", new PersonDTO(), id);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public PersonDTO create(@Validated @RequestBody PersonDTO person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getFieldErrors(), person);
		}
		return personService.create(person);
	}

	@PostMapping(value = "/upload")
	@ResponseStatus(HttpStatus.CREATED)	
	public PersonDTO upload(@RequestParam("file") MultipartFile file) {
		PersonDTO person = personService.createPersonDTO(file);
		personService.validate(person, DEFAULT_COMMAND_NAME);
		return personService.create(person);
	}

	@PutMapping
	public PersonDTO update(@Validated @RequestBody PersonDTO person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getFieldErrors(), person);
		}	
		return personService.update(person);
	}

	@DeleteMapping("/{id}")
	public PersonDTO delete(@PathVariable Integer id) {
		try {
			return personService.delete(id);			
		}
		catch (DataRetrievalFailureException cause) {
			throw new ValidationException("person.validation.message.notFound", new PersonDTO(), id);
		}
	}
}