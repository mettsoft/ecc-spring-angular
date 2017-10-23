package com.ecc.hibernate_xml.app;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PersonRegistryServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("Hello World");
	}

	@Override 
	public void destroy() {

	}
}