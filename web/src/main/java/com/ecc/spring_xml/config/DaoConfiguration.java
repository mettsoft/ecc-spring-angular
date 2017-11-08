package com.ecc.spring_xml.config;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ecc.spring_xml.model.Address;
import com.ecc.spring_xml.model.Contact;
import com.ecc.spring_xml.model.Name;
import com.ecc.spring_xml.model.Person;
import com.ecc.spring_xml.model.Role;

@Configuration
@EnableTransactionManagement
public class DaoConfiguration {
	@Bean(name = "dataSource", destroyMethod = "close")
	public BasicDataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/spring_xml");
		dataSource.setUsername("exist");
		dataSource.setPassword("ex1stgl0bal");
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactoryBean() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(getDataSource());
		sessionFactoryBean.setHibernateProperties(properties);
		sessionFactoryBean.setAnnotatedClasses(Address.class, Contact.class, Name.class, Person.class, Role.class);
		return sessionFactoryBean;
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactoryBean().getObject());
		return transactionManager;
	}
}