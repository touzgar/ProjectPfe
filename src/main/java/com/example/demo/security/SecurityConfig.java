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
        	    .requestMatchers("/getUser/**").permitAll()
        	  //  .requestMatchers("/api/manager/**").hasAuthority("ROLE_ADMIN")
        	    .requestMatchers("/api/achievementPlayer/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/achivementTeam/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/coach/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/competencesAndHistorique/**").permitAll()
        	    .requestMatchers("/api/contractPlayer/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/player/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/team/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/club/**").permitAll()
        	    .requestMatchers("/api/tournament/**","/api/defi/**").permitAll()
        	    
           	    .requestMatchers("/api/session/**","/api/scrims/**","/getCoach").hasAnyAuthority("ROLE_COACH","ROLE_Manager")
        	    
        	    .requestMatchers("/api/tournament/addMatch").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/achievementPlayer/**").hasAuthority("ROLE_Manager")
        	  //  .requestMatchers("/api/contractPlayer/**").permitAll()
        	    .requestMatchers("/api/contractPlayer/**").permitAll()
        	    //.requestMatchers("/api/achievementPlayer/add").hasAnyAuthority("ADMIN","USER")
        	    .requestMatchers("/api/sponsor/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/sponsorContract/**").permitAll()
        	    .requestMatchers("/api/ressource/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/materiel/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/logiciel/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/api/installation/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/allRoles","/addRole","/addRoleToUser/**","/all","/removeRoleByName/**","/deleteUser/**","/updateUser/**","/api/image/**").hasAnyAuthority("ROLE_ADMIN","ROLE_Manager")
        	    .requestMatchers("/addUserss","/specific-roles","/api/budget/**","/api/depense/**","/api/revenus/**").hasAuthority("ROLE_Manager")
        	    .requestMatchers("/manager-admin-roles","/addUserWithRole").hasAuthority("ROLE_ADMIN")
              	 

        
        
        
        .anyRequest().authenticated())
        
        .addFilterBefore(new JWTAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        // Enable this for debugging purposes
     //   http.httpBasic().disable().logout().disable();

        return http.build();
    }
    
    

}
