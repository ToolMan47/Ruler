package com.toolman.ruler.config.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * for Username/Password Authentication Mechanism
 * 
 * @author ToolMan
 *
 */
@Slf4j
@Component
public class MyDaoAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsService userDetailsService; // MyUserDetailsService

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

	// user from db
	UserDetails dbUser = userDetailsService.loadUserByUsername(authentication.getName());

	// user from form
	String formPwd = authentication.getCredentials().toString();

	// check pwd
	String userPwd = dbUser.getPassword();
	if (userPwd.equals(formPwd)) {
	    UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken
		    .authenticated(dbUser,
		    dbUser.getPassword(), dbUser.getAuthorities());
	    return auth;
	}
	
	log.info("Bad Credentials");
	throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
	return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
