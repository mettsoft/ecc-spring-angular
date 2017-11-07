package com.ecc.spring_xml.factory;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import com.ecc.spring_xml.dto.PersonDTO;
import com.ecc.spring_xml.util.ValidationException;

public class PersonFactory {
	public static PersonDTO createPersonDTO(byte[] data) {
		try {
			return new Gson().fromJson(IOUtils.toString(data, "utf-8"), PersonDTO.class);
		}
		catch (Exception exception) {
			throw new ValidationException("person.validation.message.invalidFormat", new PersonDTO());
		}
	}
}