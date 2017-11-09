package com.ecc.spring_security.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.ViewResolver;

@Configuration
@ComponentScan(basePackages = "com.ecc.spring_security")
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfiguration extends WebMvcConfigurerAdapter {   
	@Autowired
	private BeanFactory beanFactory;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/").setViewName("redirect:/persons");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    } 

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
 
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
        resource.setBasename("messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

    @Bean(name = "localeResolver")
    public CookieLocaleResolver getLocaleResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("localeCookie");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

	@Bean
    public LocaleChangeInterceptor getLocaleChangeInterceptor() {
         LocaleChangeInterceptor getLocaleChangeInterceptor = new LocaleChangeInterceptor();
         getLocaleChangeInterceptor.setParamName("language");
         return getLocaleChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLocaleChangeInterceptor());
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }
}