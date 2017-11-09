package com.ecc.spring_security.web;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.ecc.spring_security.dto.UserDTO;
import com.ecc.spring_security.service.UserService;

@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
 
    private static final Map<Integer, String> ROLES;
    private static final List<GrantedAuthority> ALL_ROLES;

    @Autowired
    private UserService userService;

    static {
        ROLES = new HashMap<>(6);
        ROLES.put(1, "ROLE_CREATE_PERSON");
        ROLES.put(2, "ROLE_UPDATE_PERSON");
        ROLES.put(4, "ROLE_DELETE_PERSON");
        ROLES.put(8, "ROLE_CREATE_ROLE");
        ROLES.put(16, "ROLE_UPDATE_ROLE");
        ROLES.put(32, "ROLE_DELETE_ROLE");
        ALL_ROLES = ROLES.values().stream().map(t -> new SimpleGrantedAuthority(t))
            .collect(Collectors.toList());
        ALL_ROLES.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
         
        UserDTO user = userService.get(username);
        if(user != null && user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            List<GrantedAuthority> permissions = 
                user.getPermissions() == null? 
                    ALL_ROLES: 
                        user.getPermissions().stream()
                            .map(code -> new SimpleGrantedAuthority(ROLES.get(code)))
                            .collect(Collectors.toList());
	        return new UsernamePasswordAuthenticationToken(username, password, permissions);
        }
        return null;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
}