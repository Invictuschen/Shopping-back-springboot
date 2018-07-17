package com.example.shopping.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.shopping.handler.AccessDeniedHandlerImpl;
import com.example.shopping.handler.AuthenticationEntryPointImpl;
import com.example.shopping.handler.AuthenticationFailureHandlerImpl;
import com.example.shopping.handler.AuthenticationSuccessHandlerImpl;
import com.example.shopping.handler.LogoutSuccessHandlerImpl;

public class SecurityConfig extends WebSecurityConfigurerAdapter { // 里面定义好
	@Autowired
	AuthenticationEntryPointImpl authenticationEntryPointimpl;

	@Autowired
	AccessDeniedHandlerImpl accessDeniedHandlerImpl;

	@Autowired
	AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;

	@Autowired
	AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

	@Autowired
	LogoutSuccessHandlerImpl logoutSuccessHandlerImpl;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and().authorizeRequests().antMatchers("/index.html", "/products", "/products/*")
				.permitAll().and().exceptionHandling().authenticationEntryPoint(authenticationEntryPointimpl).and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandlerImpl).and().formLogin().permitAll()
				.loginProcessingUrl("/login").successHandler(authenticationSuccessHandlerImpl)
				.failureHandler(authenticationFailureHandlerImpl).usernameParameter("username")
				.passwordParameter("password").and().logout().permitAll().logoutUrl("/logout")
				.logoutSuccessHandler(logoutSuccessHandlerImpl).and().rememberMe();
		// csrf 防止第三方攻击
		// and 连接语句 否则每次都要再写http.
		// permitall 允许所有人访问
		//
	}

	// cross origin resource sharing
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);// allow all the
																// urls
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder(11); // 设置编码加密的等级
		return encoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
