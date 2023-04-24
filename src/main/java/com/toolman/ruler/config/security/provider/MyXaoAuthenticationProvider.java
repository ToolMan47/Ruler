package com.toolman.ruler.config.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * for Username/Password Authentication Mechanism
 *
 * @author ToolMan
 */
@Slf4j
//@Component
public class MyXaoAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService; // MyUserDetailsService

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
        log.info("---------------------MyXaoAuthenticationProvider-----------------------");
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
        return true;
    }

}
