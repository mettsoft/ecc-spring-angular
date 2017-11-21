package com.ecc.spring.service;

import org.springframework.stereotype.Service;

import com.ecc.spring.model.Contact;
import com.ecc.spring.dto.ContactDTO;

@Service
public class ContactService extends AbstractService<Contact, ContactDTO> {

	public ContactService() {
		super(null);
	}

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