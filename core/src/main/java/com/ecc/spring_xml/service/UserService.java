package com.ecc.spring_xml.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

import com.ecc.spring_xml.dto.UserDTO;
import com.ecc.spring_xml.model.User;
import com.ecc.spring_xml.dao.UserDao;
import com.ecc.spring_xml.assembler.UserAssembler;
import com.ecc.spring_xml.util.AssemblerUtils;
import com.ecc.spring_xml.util.ValidationUtils;
import com.ecc.spring_xml.util.ValidationException;

@Component("userService")
public class UserService extends AbstractService<User, UserDTO> implements Validator {
	private static final Integer MAX_CHARACTERS = 255;

	private final UserDao userDao;
	private final UserAssembler userAssembler;

	public UserService(UserDao userDao, UserAssembler userAssembler) {
		super(userDao, userAssembler);
		this.userDao = userDao;
		this.userAssembler = userAssembler;
	}

	@Override
	public boolean supports(Class clazz) {
        return clazz.isAssignableFrom(UserDTO.class);
    }

    @Override
    public void validate(Object command, Errors errors) {
    	UserDTO user = (UserDTO) command;
		ValidationUtils.testNotEmpty(user.getUsername(), "username", errors, "localize:user.data.column.username");
		ValidationUtils.testMaxLength(user.getUsername(), "username", errors, MAX_CHARACTERS, "localize:user.data.column.username");
    }

	@Override
	public Serializable create(UserDTO user) {
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		return super.create(user);
	}

	public List<UserDTO> list() {
		return AssemblerUtils.asList(userDao.list(), userAssembler::createDTO);
	}

	@Override
	protected RuntimeException onCreateFailure(User user, RuntimeException cause) {
		user.setId(null);
		return onUpdateFailure(user, cause);
	}

	@Override
	protected RuntimeException onUpdateFailure(User user, RuntimeException cause) {
		if (cause instanceof DataIntegrityViolationException) {
			return new ValidationException("user.validation.message.duplicateEntry", userAssembler.createDTO(user), user.getUsername());
		}
		return super.onUpdateFailure(user, cause);
	}

	@Override
	protected RuntimeException onGetFailure(Integer id, RuntimeException cause) {
		if (cause instanceof DataRetrievalFailureException) {
			return new ValidationException("user.validation.message.notFound", new UserDTO(), id);		
		}
		return super.onGetFailure(id, cause);
	}
}