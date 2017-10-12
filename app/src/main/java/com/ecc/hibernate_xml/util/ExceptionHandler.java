package com.ecc.hibernate_xml.util;

public class ExceptionHandler {
	public static void printException(Exception exception) {
		productionPrintException(exception);
	}

	public static void productionPrintException(Exception exception) {
		System.out.println("Error: " + exception.getMessage());
	}

	public static void debugPrintException(Exception exception) {
		exception.printStackTrace();
		if (exception.getCause() != null) {
			System.out.println("Caused by:");
			exception.getCause().printStackTrace();
		}		
	}
}