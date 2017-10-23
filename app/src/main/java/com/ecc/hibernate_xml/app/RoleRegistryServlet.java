package com.ecc.hibernate_xml.app;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecc.hibernate_xml.util.TemplateEngine;

public class RoleRegistryServlet extends HttpServlet {

	private String basePath;

	@Override
	public void init() throws ServletException {
		basePath = getServletContext().getRealPath("/");
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		TemplateEngine templateEngine = new TemplateEngine(response.getWriter());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(":header", "Create new role");
		parameters.put(":body", templateEngine.renderTable());
		templateEngine.render(basePath + "/role-registry.template.html", parameters);
	}

	@Override
	public void destroy() {

	}
}