package com.toolman.ruler.config.security.filter;

import com.toolman.ruler.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

/**
 * Custom filter for JWT
 * 驗證邏輯不寫在這裡，這裡只使用manager功能去指派應該負責的 provider
 * extends AbstractAuthenticationProcessingFilter，所以驗證成功後會使用 RedirectStrategy
 * 若要關閉 redirect，必須覆蓋 AuthenticationSuccessHandler
 */
@Deprecated
@Slf4j
public class RedirectJwtTokenFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    JwtUtils jwtUtils;


    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    public RedirectJwtTokenFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
//        this.setContinueChainBeforeSuccessfulAuthentication(true);
//        this.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        // AuthenticationSuccessHandler 不做事
        this.setAuthenticationSuccessHandler((request, response, authentication) -> {});
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
    private Jwt extractToken(String header) {
        if (header == null) return null;
        String jwtToken = header.substring(BEARER_PREFIX.length());
        Jwt jwt = jwtUtils.decodeToJwt(jwtToken);

        return jwt;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("--- JwtTokenFilter ---");
        Jwt jwt = extractToken(request.getHeader(AUTHORIZATION_HEADER));

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
        return this.getAuthenticationManager().authenticate(authentication); //  to JwtProvider

    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

}
