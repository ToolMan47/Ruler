package com.toolman.ruler;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class RulerApplicationTests {
    final String issuer = "ruler";
    final String subject = "apiuser";
    final String audience = "postman";
    final String secretkey = "toolman123";


    final String token = "eyJleHAiOjE2ODIyNDE3NTEwMTUsImFsZyI6IkhTMjU2IiwiY3R5IjoidGV4dC9wbGFpbiJ9.eyJzdWIiOiJhcGl1c2VyIiwiYXVkIjoicG9zdG1hbiIsImlzcyI6InJ1bGVyIiwiZXhwIjoxNjgyMjQ1MzUxLCJpYXQiOjE2ODIyNDE3NTEsImp0aSI6IjhlMmVlMzY2LTc2Y2EtNDE0Ni1iMjM1LTM2YTcyYTFlN2RkZiJ9.KxAAW45bOX5Warq8xjhFZ5l_RuYh0y5s34rkRLYBy_8";


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
        SignedJWT signedJWT = SignedJWT.parse(token);
        Header header = signedJWT.getHeader();
        Payload payload = signedJWT.getPayload();
        Base64URL signature = signedJWT.getSignature();


        byte[] hashedKey = MessageDigest.getInstance("SHA-256").digest(secretkey.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashedKey, "SHA-256");


        JWSVerifier verifier = new MACVerifier(secretKeySpec);
        boolean isVerify = signedJWT.verify(verifier);
        Assert.isTrue(isVerify);
    }

}
