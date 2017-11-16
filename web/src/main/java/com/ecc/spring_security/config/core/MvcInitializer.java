package com.ecc.spring_security.config.core;

import javax.servlet.ServletRegistration;
import javax.servlet.MultipartConfigElement;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ecc.spring_security.config.AppConfiguration;
import com.ecc.spring_security.config.MvcConfiguration;
import com.ecc.spring_security.config.SecurityConfiguration;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfiguration.class, MvcConfiguration.class, SecurityConfiguration.class  };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override 
	protected String getServletName() {
		return "MvcServlet";
	}

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfigElement = 
            new MultipartConfigElement(System.getProperty("java.io.tmpdir"));

        registration.setMultipartConfig(multipartConfigElement);
    }
}