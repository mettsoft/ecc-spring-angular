package com.ecc.spring.util;

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