package com.ecc.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.ecc.spring.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Override
  public void configure(WebSecurity web) throws Exception {
      web.ignoring().regexMatchers("^.*\\.html$", "^.*\\.css*$", "^.*\\.js$");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers("/users/permissions").authenticated()
          .antMatchers("/users/**").hasRole("ADMIN")
          .antMatchers(HttpMethod.POST, "/persons").hasRole("CREATE_PERSON")
          .antMatchers(HttpMethod.POST, "/persons/upload").hasRole("CREATE_PERSON")
          .antMatchers(HttpMethod.PUT, "/persons").hasRole("UPDATE_PERSON")
          .antMatchers(HttpMethod.DELETE, "/persons/*").hasRole("DELETE_PERSON")
          .antMatchers(HttpMethod.POST, "/roles").hasRole("CREATE_ROLE")
          .antMatchers(HttpMethod.PUT, "/roles").hasRole("UPDATE_ROLE")
          .antMatchers(HttpMethod.DELETE, "/roles/*").hasRole("DELETE_ROLE")
          .antMatchers("/").permitAll()
          .antMatchers("/resources").permitAll()
          .anyRequest().authenticated()
          .and()
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
        .httpBasic()
          .and()
        .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
  }

  @Bean 
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
}