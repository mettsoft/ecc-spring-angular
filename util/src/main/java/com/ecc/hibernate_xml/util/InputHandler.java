package com.ecc.hibernate_xml.util;

import java.util.Scanner;
import com.ecc.hibernate_xml.util.CheckedFunction;

public class InputHandler {
	private static final Scanner SCANNER = new Scanner(System.in);

	public static String getNextLine(String message) {
		System.out.print(message);
		return SCANNER.nextLine();		
	}

	public static <R> R getNextLine(String message, CheckedFunction<String, R> function) throws InputException {
		System.out.print(message);
		try {
			return function.apply(SCANNER.nextLine());				
		}
		catch (Exception exception) {
			throw new InputException(exception);
		}
	}

	public static <R> R getNextLineREPL(String message, CheckedFunction<String, R> function) {
		System.out.print(message);
		try {
			return function.apply(SCANNER.nextLine());				
		}
		catch (NumberFormatException exception) {
			System.out.println("Error: " + new InputException(exception).getMessage());
			return getNextLineREPL(message, function);
		}
		catch (Exception exception) {
			System.out.println("Error: " + exception.getMessage());
			return getNextLineREPL(message, function);
		}
	}
}