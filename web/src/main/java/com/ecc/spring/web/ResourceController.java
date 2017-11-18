package com.ecc.spring.web;

import java.util.Map;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecc.spring.util.ResourceUtils;

@RestController
public class ResourceController {

	@GetMapping("resources")
	public Map<String, Properties> getLocalizedDictionary() {
		return ResourceUtils.getLocalizedDictionary();
	}
}