package com.toolman.ruler.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.toolman.ruler.config.security.provider.MyDaoAuthenticationProvider;

/**
 * 
 * @author ToolMan
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final String[] resources = { "/css/*", "/js/*", "/images/*", "*/favicon.ico" };

    @Autowired
    private MyDaoAuthenticationProvider daoAuthenticationProvider;

    // security filter for path
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	// defense
	http.cors().and().csrf();

	// 一般公開頁面
	http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/", "/intro")
		.permitAll()
		.requestMatchers("/login") // 避免出現 ERR_TOO_MANY_REDIRECTS
		.permitAll()
		.requestMatchers("/logout") // 避免出現 ERR_TOO_MANY_REDIRECTS
		.permitAll()
		.requestMatchers("/error")
		.permitAll());

	// 登入處理
	http.formLogin(form -> form.loginPage("/login").failureUrl("/error"));
	
	http.logout(out -> out.logoutUrl("/logout"));
	
	return http.build();
    }

    // add auth provider
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
	AuthenticationManagerBuilder authManagerBuild = http.getSharedObject(AuthenticationManagerBuilder.class);
	authManagerBuild.authenticationProvider(daoAuthenticationProvider);
//	authManagerBuild.authenticationProvider(new MyDaoAuthenticationProvider());

	return authManagerBuild.build();
    }

    // method 2
//    @Bean
//    public AuthenticationManager authManager(AuthenticationManagerBuilder builder) throws Exception {
//	builder.authenticationProvider(null);
//	
//	return builder.build();
//    }

    // 靜態資源取得
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
	return (web) -> web.ignoring().requestMatchers(resources);
    }

}
