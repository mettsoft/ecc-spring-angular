package com.ecc.spring_security.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ecc.spring_security.dto.RoleDTO;
import com.ecc.spring_security.service.RoleService;
import com.ecc.spring_security.util.ValidationException;

@RestController("restRoleController")
@RequestMapping("/api/roles")
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
		return roleService.get(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)	
	public void create(@Validated RoleDTO role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), role);
		}
		roleService.create(role);
	}

	@PutMapping
	public void update(@Validated RoleDTO role, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getAllErrors(), role);
		}	
		roleService.update(role);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		roleService.delete(id);	
	}
}