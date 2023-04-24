package com.toolman.ruler.config.security.provider;

import com.toolman.ruler.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyJwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Jwt jwt = token.getToken();
        String tokenValue = jwt.getTokenValue();

        // try catch
        jwtUtils.verifyToken(tokenValue);
        authentication.setAuthenticated(true);
        // TODO provide Authority


        log.info("JwtAuthenticationToken authenticate");
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("---------------------MyJwtAuthenticationProvider-----------------------");
        return authentication.equals(JwtAuthenticationToken.class);
    }
}
