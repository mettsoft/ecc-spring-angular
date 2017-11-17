package com.ecc.spring.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice("com.ecc.spring.web")
public class GlobalControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@ExceptionHandler({ Exception.class })
	public void exceptionHandler(Exception cause) throws Exception {
		logger.error("Fatal Error!", cause);
		throw cause;
	}
}