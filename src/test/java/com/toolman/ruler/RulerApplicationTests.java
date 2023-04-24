package com.toolman.ruler;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.toolman.ruler.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class RulerApplicationTests {
    @Autowired
    JwtUtils jwtUtils;
    final String issuer = "ruler";
    final String subject = "apiuser";
    final String audience = "postman";
    final String secretkey = "ruler-secret-key-safe-over-32-length";
    final String tokenValue = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJydWxlciIsInN1YiI6ImFwaXVzZXIiLCJhdWQiOiJwb3N0bWFuIiwiaWF0IjoxNjgyMzI5MzA5LCJleHAiOjE2ODIzMzI5MDksImp0aSI6IjBjMTkxYWUxLTgyZjQtNDQ4NS04Yzc1LTJlNWZkODFjN2EyYiJ9.95-QlTzxVWy4Sd4DL6s7DfaVq82VF7KaYg9NDulmv0Y";


    @Test
    void contextLoads() {

        System.out.println("Hi Test");
    }


    @Test
    void bcryptTest() {
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        String encodePwd = crypt.encode("0000");
        System.out.println(encodePwd);
    }


    @Test
    void generateJWT() throws ParseException, JOSEException, NoSuchAlgorithmException {
        // header.payload.signature

        // Create header
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).contentType("text/plain")
                .customParam("exp", new Date().getTime())
                .build();

        Instant now = Instant.now();

        // Create payload
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(subject)
                .audience(audience)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plusSeconds(3600)))
                .jwtID(UUID.randomUUID().toString())
                .build();

        byte[] hashedKey = MessageDigest.getInstance("SHA-256").digest(secretkey.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashedKey, "SHA-256");

        // Create JWS object
        JWSSigner signer = new MACSigner(secretKeySpec);
        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));

        jwsObject.sign(signer);


        System.out.println(jwsObject.getHeader().toString());
        System.out.println(jwsObject.getPayload().toString());
        System.out.println(jwsObject.getSignature().toString());

        System.out.println(jwsObject.serialize());
    }

    @Test
    void verifyJWT() throws ParseException, NoSuchAlgorithmException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(tokenValue);
        Header header = signedJWT.getHeader();
        Payload payload = signedJWT.getPayload();
        Base64URL signature = signedJWT.getSignature();


        byte[] hashedKey = MessageDigest.getInstance("SHA-256").digest(secretkey.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashedKey, "SHA-256");


        JWSVerifier verifier = new MACVerifier(secretKeySpec);
        boolean isVerify = signedJWT.verify(verifier);
        Assert.isTrue(isVerify);
    }

    @Test
    void stringToJwt() throws NoSuchAlgorithmException {
        byte[] hashedKey = MessageDigest.getInstance("SHA-256").digest(secretkey.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashedKey, "SHA-256");
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).build();


        Jwt au = jwtDecoder.decode(tokenValue);
        System.out.println(au.getHeaders());
        System.out.println(au.getClaims());

    }

    @Test
    void generateWithJJwt() {
        SecretKey key = Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
        Instant now = Instant.now();


        String jws = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(6000)))
                .setId(UUID.randomUUID().toString())
                .signWith(key)
                .compact();

        System.out.println(jws);
    }

    @Test
    void parseWithJJwt() {
        SecretKey key = Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
        Instant now = Instant.now();
        String unsignJwt = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(3600)))
                .setId(UUID.randomUUID().toString())
                .compact();
        String signJwt = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(3600)))
                .setId(UUID.randomUUID().toString())
                .signWith(key)
                .compact();



        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(unsignJwt);
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(signJwt);
    }


    @Test
    void jwt2Jwt() {
//        Jwt jwt = Jwt.withTokenValue(tokenValue).build();
        Jwt jwt = jwtUtils.decodeToJwt(tokenValue);

    }


}
