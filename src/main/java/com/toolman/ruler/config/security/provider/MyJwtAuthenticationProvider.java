package com.toolman.ruler.config.security.provider;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@Slf4j
//@Component
public class MyJwtAuthenticationProvider implements AuthenticationProvider {

    private final String secretkey = "toolman123";



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;

        Jwt jwt = token.getToken();

        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(jwt.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        byte[] hashedKey = new byte[0];
        try {
            hashedKey = MessageDigest.getInstance("SHA-256").digest(secretkey.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashedKey, "SHA-256");


        JWSVerifier verifier = null;
        try {
            verifier = new MACVerifier(secretKeySpec);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        try {
            if (signedJWT.verify(verifier)) {
                String subject = jwt.getSubject();
                log.info("Authenticated token user: {}", subject);
                return new JwtAuthenticationToken(jwt);
            }

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        log.info("Authenticated token fail");
        return null;
    }

    private Jwt getJwt(JwtAuthenticationToken bearer) {
        try {
            return bearer.getToken();
        }
        catch (BadJwtException failed) {
            log.info("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        }
        catch (JwtException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("---------------------MyJwtAuthenticationProvider-----------------------");
//        return authentication.equals(JwtAuthenticationToken.class);
        return true;
    }
}
