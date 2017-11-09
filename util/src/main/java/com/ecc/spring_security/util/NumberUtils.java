package com.ecc.spring_security.util;

public class NumberUtils {
	public static Integer createInteger(String number) {
		try {
			return Integer.valueOf(number);
		}
		catch (NumberFormatException cause) {
			return null;
		}
 	}
}