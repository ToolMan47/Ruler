package com.toolman.ruler.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * JWT convert, verify, generate
 * <p>
 * Because of JJWT’s fluent interface, the creation of the JWT is basically a three-step process:
 * <p>
 * The definition of the internal claims of the token, like Issuer, Subject, Expiration, and ID
 * The cryptographic signing of the JWT (making it a JWS)
 * The compaction of the JWT to a URL-safe string, according to the JWT Compact Serialization rules
 */
@Slf4j
public class JwtUtils {

    private final String secret;
    private final SecretKey SECRET_KEY;

    public JwtUtils(String secret) {
        this.secret = secret;
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public void verifyToken(String tokenValue) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {

        Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(tokenValue);
    }

    public Jwt decodeToJwt(String tokenValue) {
        log.info("decodeToJwt: {}", tokenValue);
        SecretKey SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        JwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(SECRET_KEY).build();
        Jwt jwt = jwtDecoder.decode(tokenValue);
        return jwt;
    }

}
