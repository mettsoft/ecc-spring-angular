package com.ecc.hibernate_xml.util;

import java.text.ParseException;
import java.util.Scanner;

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
		catch (NumberFormatException|ParseException exception) {
			ExceptionHandler.printException(new InputException(exception));
			return getNextLineREPL(message, function);
		}
		catch (Exception exception) {
			ExceptionHandler.printException(exception);
			return getNextLineREPL(message, function);
		}
	}

	public static void consumeNextLineREPL(String message, CheckedConsumer<String> function) {
		getNextLineREPL(message, input -> {
			function.accept(input);
			return 0;
		});
	}
}