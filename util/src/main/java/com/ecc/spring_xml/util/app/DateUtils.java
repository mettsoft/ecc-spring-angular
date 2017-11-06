package com.ecc.spring_xml.util.app;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static Date parse(String date) {
		try {
			return DATE_FORMAT.parse(date);
		}
		catch (ParseException|NullPointerException cause) {
			return null;
		}
 	}

 	public static String toString(Date date) {
 		return DATE_FORMAT.format(date);
 	}
}