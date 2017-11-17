package com.ecc.spring.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.ecc.spring.factory.PersonFactory;
import com.ecc.spring.service.PersonService;
import com.ecc.spring.util.DateUtils;
import com.ecc.spring.util.ValidationException;

@RestController
@RequestMapping("/persons")
public class PersonController {
	private static final String DEFAULT_COMMAND_NAME = "command";

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonFactory personFactory;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		if (binder.getTarget() != null && personService.supports(binder.getTarget().getClass())) {
			binder.setValidator(personService);		
		}
    binder.registerCustomEditor(Date.class, new CustomDateEditor(DateUtils.DATE_FORMAT, true));
	}

	@GetMapping
	public List<PersonDTO> list(
		@RequestParam(value = "queryLastName", required = false) String lastName,
		@RequestParam(value = "queryRoleId", required = false) Integer roleId,
		@RequestParam(value = "queryBirthday", required = false) Date birthday,
		@RequestParam(value = "queryOrderBy", required = false) String orderBy,
		@RequestParam(value = "queryOrderType", required = false) String orderType) {
		return personService.list(lastName, roleId, birthday, orderBy, orderType);
	}

	@GetMapping("/{id}")
	public PersonDTO get(@PathVariable Integer id) {
		return personService.get(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public void create(@Validated PersonDTO person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), person);
		}
		personService.create(person);
	}

	@PostMapping(value = "/upload")
	@ResponseStatus(HttpStatus.CREATED)	
	public void upload(@RequestParam("file") MultipartFile file) {
		PersonDTO person = personFactory.createPersonDTO(file);
		personService.validate(person, DEFAULT_COMMAND_NAME);
		personService.create(person);
	}

	@PutMapping
	public void update(@Validated PersonDTO person, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), person);
		}	
		personService.update(person);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		personService.delete(id);	
	}
}