package com.ecc.spring_xml.web;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.ecc.spring_xml.dto.UserDTO;
import com.ecc.spring_xml.service.UserService;

@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
 
 	@Autowired
 	private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
         
        UserDTO user = userService.get(username);
        if(user != null && user.getPassword().equals(DigestUtils.sha256Hex(password))) {
	        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        }
        return null;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
}