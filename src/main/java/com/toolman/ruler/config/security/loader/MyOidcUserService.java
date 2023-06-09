package com.toolman.ruler.config.security.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

/**
 *
 * Custom OidcUserService for oidc eg.google
 */
@Slf4j
@Service
public class MyOidcUserService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        // TODO customize
        log.info("customize MyOidcUserService:{}", userRequest);

        return super.loadUser(userRequest);
    }
}
