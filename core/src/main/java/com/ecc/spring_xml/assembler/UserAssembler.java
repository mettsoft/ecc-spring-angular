package com.ecc.spring_xml.assembler;

import org.springframework.stereotype.Component;

import com.ecc.spring_xml.model.User;
import com.ecc.spring_xml.dto.UserDTO;

@Component
public class UserAssembler implements Assembler<User, UserDTO> {
	@Override
	public UserDTO createDTO(User model) {
		if (model == null) {
			return null;
		}
		UserDTO dto = new UserDTO();
		dto.setId(model.getId());
		dto.setUsername(model.getUsername());
		dto.setPassword(model.getPassword());

		for (int code = model.getPermissions(), count = 0; code != 0; code >>>= 1, count++) {
			if ((code & 1) == 1) {
				dto.getPermissions().add(1 << count);					
			} 
		}
		return dto;
	}

	@Override 
	public User createModel(UserDTO dto) {
		if (dto == null) {
			return null;
		}
		User model = new User();
		model.setId(dto.getId());
		model.setUsername(dto.getUsername());
		model.setPassword(dto.getPassword());

		int code = 0;
		for (int i = 0; i < dto.getPermissions().size(); i++) {
			code |= dto.getPermissions().get(i);
		}
		model.setPermissions(code);
		return model;
	}
}