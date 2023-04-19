package com.toolman.ruler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 
 * @author ToolMan
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    String[] resources = { "/css/*", "/js/*", "/images/*", "*/favicon.ico" };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	// defense
	http.cors().and().csrf();

	// 一般公開頁面
	http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/", "/intro")
		.permitAll()
		.requestMatchers("/login") // 避免出現 ERR_TOO_MANY_REDIRECTS 
		.permitAll());

	// 登入處理
	http.formLogin(form -> form.loginPage("/login").failureUrl("/login?error"));

	return http.build();

    }

    // 靜態資源取得
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
	return (web) -> web.ignoring().requestMatchers(resources);
    }

}
