package com.ecc.spring.config;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ecc.spring.model.Address;
import com.ecc.spring.model.Contact;
import com.ecc.spring.model.Name;
import com.ecc.spring.model.Person;
import com.ecc.spring.model.Role;
import com.ecc.spring.model.User;
import com.ecc.spring.model.Permission;

@Configuration
@ComponentScan(basePackages = "com.ecc.spring", 
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.ecc.spring.config.*")
	})
@EnableTransactionManagement(proxyTargetClass = true)
public class AppConfiguration {
	@Bean(destroyMethod = "close")
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/spring");
		dataSource.setUsername("exist");
		dataSource.setPassword("ex1stgl0bal");
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setHibernateProperties(properties);
		sessionFactoryBean.setAnnotatedClasses(Address.class, Contact.class, Name.class, 
			Person.class, Role.class, User.class, Permission.class);
		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactoryBean().getObject());
		return transactionManager;
	}
}