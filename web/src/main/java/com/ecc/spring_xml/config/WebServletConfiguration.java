package com.ecc.spring_xml.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.FilterRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebServletConfiguration implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(AppConfiguration.class, WebSecurityConfiguration.class);
		appContext.setServletContext(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("App", new DispatcherServlet(appContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");

		FilterRegistration.Dynamic filter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
		filter.addMappingForUrlPatterns(null, true, "/*");
	}
}