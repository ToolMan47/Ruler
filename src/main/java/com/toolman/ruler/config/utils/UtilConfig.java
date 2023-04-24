package com.toolman.ruler.config.utils;

import com.toolman.ruler.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

    @Value("${token.secret}")
    private String secret;


    @Bean
    public JwtUtils jwtUtils() {
        JwtUtils jwtUtilsInstance = new JwtUtils(secret);
        return jwtUtilsInstance;
    }


}
