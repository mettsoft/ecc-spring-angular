package com.ecc.spring.web;

import java.util.List;
import java.util.stream.Collectors;

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

import com.ecc.spring.dto.RoleDTO;
import com.ecc.spring.service.RoleService;
import com.ecc.spring.util.ValidationException;

@RestController
@RequestMapping("roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(roleService);
	}

	@GetMapping
	public List<RoleDTO> list() {
		return roleService.list();
	}

	@GetMapping("/{id}")
	public RoleDTO get(@PathVariable Integer id) {
		try {
			return roleService.get(id);
		}
		catch (DataRetrievalFailureException cause) {
			throw new ValidationException("role.validation.message.notFound", new RoleDTO(), id);		
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public void create(@Validated RoleDTO role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), role);
		}
		try {
			roleService.create(role);		
		}
		catch(DataIntegrityViolationException cause) {
			throw new ValidationException("role.validation.message.duplicateEntry", role, role.getName());
		}
	}

	@PutMapping
	public void update(@Validated RoleDTO role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), role);
		}	
		try {
			roleService.update(role);		
			role = roleService.get(role.getId());		
		}
		catch(DataRetrievalFailureException cause) {
			throw new ValidationException("role.validation.message.notFound", new RoleDTO(), role.getId());		
		}
		catch(DataIntegrityViolationException cause) {
			throw new ValidationException("role.validation.message.duplicateEntry", role, role.getName());
		}
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		RoleDTO role = null;
		try {
			role = roleService.get(id);
			roleService.delete(id);
		}
		catch(DataIntegrityViolationException cause) {
			if (role.getPersons().size() > 0) {
				String personNames = role.getPersons()
					.stream()
					.map(person -> person.getName().toString())
					.collect(Collectors.joining("; "));
				throw new ValidationException("role.validation.message.inUsed", role, personNames);
			}
			throw cause;
		}
		catch (DataRetrievalFailureException cause) {
			throw new ValidationException("role.validation.message.notFound", new RoleDTO(), role.getId());		
		}
	}
}