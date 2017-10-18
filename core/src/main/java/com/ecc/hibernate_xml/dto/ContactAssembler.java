package com.ecc.hibernate_xml.dto;

import com.ecc.hibernate_xml.dto.ContactDTO;
import com.ecc.hibernate_xml.model.Contact;
import com.ecc.hibernate_xml.model.Landline;
import com.ecc.hibernate_xml.model.Email;
import com.ecc.hibernate_xml.model.MobileNumber;

public class ContactAssembler extends AbstractAssembler<Contact, ContactDTO> {
	private PersonAssembler personAssembler = new PersonAssembler();
	
	@Override
	public ContactDTO createDTO(Contact model) {
		ContactDTO dto = new ContactDTO();
		dto.setId(model.getId());
		dto.setData(model.getData());
		dto.setPerson(personAssembler.createDTO(model.getPerson()));
		dto.setContactType(model.getContactType());
		return dto;
	}

	@Override 
	public Contact createModel(ContactDTO dto) {
		Contact model = null;

		switch(dto.getContactType()) {
			case "Landline": 
				model = new Landline();
				break;
			case "Email":
				model = new Email();
				break;
			case "Mobile Number":
				model = new MobileNumber();
				break;
		}

		model.setId(dto.getId());
		model.setData(dto.getData());
		model.setPerson(personAssembler.createModel(dto.getPerson()));
		return model;
	}
}