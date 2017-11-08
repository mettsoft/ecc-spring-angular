package com.ecc.spring_xml.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DaoConfiguration {
	@Autowired
	private ResourceLoader resourceLoader;

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
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(getDataSource());
		sessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:/persistence/hibernate.cfg.xml"));
		return sessionFactoryBean;
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactoryBean().getObject());
		return transactionManager;
	}
}