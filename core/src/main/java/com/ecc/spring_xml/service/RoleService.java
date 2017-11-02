package com.ecc.spring_xml.service;

import java.util.List;

import com.ecc.spring_xml.dto.RoleDTO;
import com.ecc.spring_xml.model.Role;
import com.ecc.spring_xml.dao.RoleDao;
import com.ecc.spring_xml.assembler.RoleAssembler;
import com.ecc.spring_xml.util.app.AssemblerUtils;
import com.ecc.spring_xml.util.validator.ValidationException;
import com.ecc.spring_xml.util.validator.ModelValidator;

public class RoleService extends AbstractService<Role, RoleDTO> {
	private static final Integer MAX_CHARACTERS = 20;

	private final RoleDao roleDao;
	private final RoleAssembler roleAssembler;
	private final ModelValidator validator;

	public RoleService(RoleDao roleDao, RoleAssembler roleAssembler, ModelValidator validator) {
		super(roleDao, roleAssembler);
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
		this.validator = validator;
	}

	public void validate(RoleDTO role) throws ValidationException {
		validator.validate("NotEmpty", role.getName(), "Role name");
		validator.validate("MaxLength", role.getName(), MAX_CHARACTERS, "Role name");
	}

	public List<RoleDTO> list() {
		return AssemblerUtils.asList(roleDao.list(), roleAssembler::createDTO);
	}
}