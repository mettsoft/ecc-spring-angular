package com.ecc.spring.util;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

public class ResourceUtils {
	public static Map<String, Properties> getLocalizedDictionary() {
		Map<String, Properties> dictionary = new HashMap<>(2);
		try {
			Properties englishDictionary = new Properties();
			englishDictionary.load(ResourceUtils.class.getClassLoader().getResourceAsStream("/messages.properties"));

			Properties filipinoDictionary = new Properties();
			filipinoDictionary.load(ResourceUtils.class.getClassLoader().getResourceAsStream("/messages_fil.properties"));
			
			dictionary.put("en", englishDictionary);
			dictionary.put("fil", filipinoDictionary);
			return dictionary;			
		}
		catch(Exception cause) {
			throw new RuntimeException("Parsing messages.propeties failed!", cause);
		}
	}
}