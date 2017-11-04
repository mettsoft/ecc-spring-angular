package com.ecc.spring_xml.factory;

import java.io.IOException;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import com.ecc.spring_xml.dto.PersonDTO;

public class PersonFactory {
	public static PersonDTO createPersonDTO(byte[] data) {
		try {
			return new Gson().fromJson(IOUtils.toString(data, "utf-8"), PersonDTO.class);		
		}
		catch (Exception exception) {
			throw new RuntimeException("Invalid file format!");
		}
	}
}