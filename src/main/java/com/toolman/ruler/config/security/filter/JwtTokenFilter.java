package com.toolman.ruler.config.security.filter;

import com.toolman.ruler.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom filter for JWT
 * 驗證邏輯不寫在這裡，這裡只使用manager功能去指派應該負責的 provider
 */
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final AuthenticationManager authenticationManager;


    public JwtTokenFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("--- JwtTokenFilter ---");
        Jwt jwt = extractToken(request.getHeader(AUTHORIZATION_HEADER));
        if (jwt != null) {
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
            authenticationManager.authenticate(authentication); //  to JwtProvider
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private Jwt extractToken(String header) {
        if (header == null) return null;
        String jwtToken = header.substring(BEARER_PREFIX.length());
        Jwt jwt = jwtUtils.decodeToJwt(jwtToken);
        return jwt;
    }
}
