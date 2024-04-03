package com.example.demo.security;

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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

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
        	    .requestMatchers("/login", "/register/**", "/verifyEmail/**", "/forgot-password","/reset-password", "/logout").permitAll()
        	    .requestMatchers("/all","/addRoleToUser/**","/getUser/**","/removeRole/**","/deleteUser/**","/addRole","/allRoles").permitAll()
        	    .requestMatchers("/api/manager/**").permitAll()
        	    .requestMatchers("/api/achievementPlayer/**").permitAll()
        	    .requestMatchers("/api/achivementTeam/**").permitAll()
        	    .requestMatchers("/api/coach/**").permitAll()
        	    .requestMatchers("/api/competencesAndHistorique/**").permitAll()
        	    .requestMatchers("/api/contractPlayer/**").permitAll()
        	    .requestMatchers("/api/player/**").permitAll()
        	    .requestMatchers("/api/team/**").permitAll()
        	    .requestMatchers("/api/club/**").permitAll()
        	    .requestMatchers("/api/tournament/**").permitAll()
        	    .requestMatchers("/api/defi/**").permitAll()
        	    .requestMatchers("/api/session/**").permitAll()
        	    .requestMatchers("/api/scrims/**").permitAll()
        	    .requestMatchers("/api/tournament/addMatch").permitAll()
        	    .requestMatchers("/api/achievementPlayer/**").permitAll()
        	  //  .requestMatchers("/api/contractPlayer/**").permitAll()
        	    .requestMatchers("/api/contractPlayer/**").permitAll()
        	    //.requestMatchers("/api/achievementPlayer/add").hasAnyAuthority("ADMIN","USER")
        	    .requestMatchers("/api/sponsor/**").permitAll()
        	    .requestMatchers("/api/sponsorContract/**").permitAll()
        	    .requestMatchers("/api/ressource/**").permitAll()
        	    .requestMatchers("/api/materiel/**").permitAll()
        	    .requestMatchers("/api/logiciel/**").permitAll()
        	    .requestMatchers("/api/installation/**").permitAll()
        	    
              	 

        
        
        
        .anyRequest().authenticated())
        
        .addFilterBefore(new JWTAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        // Enable this for debugging purposes
     //   http.httpBasic().disable().logout().disable();

        return http.build();
    }
    
    

}
