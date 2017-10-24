package com.ecc.hibernate_xml.util.app;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static Date parse(String date) {
		try {
			return DATE_FORMAT.parse(date);
		}
		catch (ParseException cause) {
			return null;
		}
 	}
}