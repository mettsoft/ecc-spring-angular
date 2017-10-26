package com.ecc.servlets.util.app;

public class ExceptionHandler {
	public static String printException(Exception cause) {
		cause.printStackTrace();
		if (cause.getCause() != null) {
			System.out.println("Caused by: ");
			cause.getCause().printStackTrace();
		}
		return "<h3 style=\"color: red\"> Error: " + cause.getMessage() + "</h3>";
	}
}