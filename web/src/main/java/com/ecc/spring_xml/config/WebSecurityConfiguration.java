package com.ecc.spring_xml.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.ecc.spring_xml.web.DatabaseAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DatabaseAuthenticationProvider authProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers("/persons/create").hasRole("CREATE_PERSON")
                .antMatchers("/persons/update").hasRole("UPDATE_PERSON")
                .antMatchers("/persons/delete").hasRole("DELETE_PERSON")
                .antMatchers("/roles/create").hasRole("CREATE_ROLE")
                .antMatchers("/roles/update").hasRole("UPDATE_ROLE")
                .antMatchers("/roles/delete").hasRole("DELETE_ROLE")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
        http.csrf().disable();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
}