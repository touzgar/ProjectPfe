package com.example.demo.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity


public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter(tokenStore);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        
        http.sessionManagement(session -> session
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable())
        
        .cors(cors ->cors.configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cors=new CorsConfiguration();
				cors.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
				cors.setAllowedMethods(Collections.singletonList("*"));
				cors.setAllowedHeaders(Collections.singletonList("*"));
				cors.setExposedHeaders(Collections.singletonList("Authorization"));
				return cors;
			}
		}))
        
        
        .authorizeRequests(requests -> requests
        	    .requestMatchers("/login", "/register/**", "/verifyEmail/**", "/forgot-password", "/reset-password" ).permitAll()
        	    .requestMatchers("/all", "/addRoleToUser/**", "/getUser/**", "/removeRole/**","/allRoles","/addRole","/deleteUser/**").permitAll()
        	    .requestMatchers("/api/player/**").hasAnyAuthority("ADMIN","Manager")
        	    .anyRequest().authenticated())
        	.addFilterBefore(new JWTAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        	.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
  
        // Enable this for debugging purposes
     //   http.httpBasic().disable().logout().disable();

        return http.build();
    }
    
    

}
