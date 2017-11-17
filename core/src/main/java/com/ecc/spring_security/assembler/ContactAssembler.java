package com.ecc.spring.assembler;

import org.springframework.stereotype.Component;

import com.ecc.spring.model.Contact;
import com.ecc.spring.dto.ContactDTO;

@Component
public class ContactAssembler implements Assembler<Contact, ContactDTO> {
	@Override
	public ContactDTO createDTO(Contact model) {
		if (model == null) {
			return null;
		}
		ContactDTO dto = new ContactDTO();
		dto.setId(model.getId());
		dto.setData(model.getData());
		dto.setContactType(model.getContactType());
		return dto;
	}

	@Override 
	public Contact createModel(ContactDTO dto) {
		if (dto == null) {
			return null;
		}
		Contact model = new Contact();
		model.setId(dto.getId());
		model.setData(dto.getData());
		model.setContactType(dto.getContactType());
		return model;
	}
}