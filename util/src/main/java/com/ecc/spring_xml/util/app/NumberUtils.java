package com.ecc.spring_xml.util.app;

import java.math.BigDecimal;

public class NumberUtils {
	public static Integer createInteger(String number) {
		try {
			return Integer.valueOf(number);
		}
		catch (NumberFormatException cause) {
			return null;
		}
 	}
	public static BigDecimal createBigDecimal(String number) {
		try {
			return new BigDecimal(number);
		}
		catch (NumberFormatException|NullPointerException cause) {
			return null;
		}
 	}
}