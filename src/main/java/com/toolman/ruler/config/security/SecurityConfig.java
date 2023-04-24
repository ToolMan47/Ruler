package com.toolman.ruler.config.security;

import com.toolman.ruler.config.security.provider.MyDaoAuthenticationProvider;
import com.toolman.ruler.config.security.provider.MyJwtAuthenticationProvider;
import com.toolman.ruler.config.security.provider.MyXaoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author ToolMan
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${spring.security.holly-debug:false}")
    boolean webSecurityDebug;



    final String[] resources = {"/css/*", "/js/*", "/images/*", "*/favicon.ico"};


    // security filter for path
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // defense
        http.cors().and().csrf();

        // Spring Security Default 設定: 全部都進入管控，就算是 "/" 也是
        // 先設定要驗證的路徑，其餘的可以隨意存取
        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/user")
                .authenticated()
                .requestMatchers("/admin")
                .hasAuthority("ADMIN")

                .anyRequest()
                .permitAll());

        // 登入處理
        http.formLogin(form -> form.loginPage("/login").failureUrl("/error"));

        // oauth2
        http.oauth2Login(oauth -> oauth.loginPage("/login").failureUrl("/error"));

        // 登出處理
        http.logout();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(userDetailsService);
        daoAuthProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthProvider;
    }

    // 不需要此配置，只要 implements AuthenticationProvider 的 Bean 就會用到 authenticate
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuild = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuild.authenticationProvider(new MyDaoAuthenticationProvider());
        authManagerBuild.authenticationProvider(new MyJwtAuthenticationProvider());
        authManagerBuild.authenticationProvider(new MyXaoAuthenticationProvider());
        authManagerBuild.authenticationProvider(new DaoAuthenticationProvider());

        return authManagerBuild.build();
    }

    // 強力 debug
    // to log specific info about requests and applied filters
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(webSecurityDebug);
    }

}
