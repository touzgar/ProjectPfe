package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	 private TokenStore tokenStore;

	    public JWTAuthorizationFilter(TokenStore tokenStore) {
	        super();
	        this.tokenStore = tokenStore;
	    }
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	    	   response.setHeader("Access-Control-Allow-Origin","*");
	           response.setHeader("Access-Control-Allow-Methods",
	                   "GET, POST, DELETE, PUT, PATCH");
	           response.setHeader("Access-Control-Allow-Headers",
	                   "Origin, Accept, X-Requested-With, Content-Type, " +
	                           "Access-Control-Request-Method, " +
	                           "Access-Control-Request-Headers, " +
	                           "Authorization");
	           response.setHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, " + "Access-Control-Allow-Credentials, Authorization");
	        
	    		        String jwtToken = request.getHeader("Authorization");
	        if (jwtToken != null && jwtToken.startsWith(SecParams.PREFIX)) {
	            try {
	                jwtToken = jwtToken.substring(SecParams.PREFIX.length());
	                if (tokenStore.isTokenBlacklisted(jwtToken)) {
	                    throw new JWTVerificationException("Token is blacklisted");
	                }
	                Algorithm algorithm = Algorithm.HMAC256(SecParams.SECRET);
	                JWTVerifier verifier = JWT.require(algorithm).build();
	                DecodedJWT decodedJWT = verifier.verify(jwtToken);
	             // New code starts here
	             String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
	             List<GrantedAuthority> authorities = new ArrayList<>();
	             for (String role : roles) {
	                 authorities.add(new SimpleGrantedAuthority(role));
	             }
	             UsernamePasswordAuthenticationToken authenticationToken = 
	                 new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
	             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	             // New code ends here
	             filterChain.doFilter(request, response);

	            } catch (JWTVerificationException exception) {
	                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                response.sendError(HttpServletResponse.SC_FORBIDDEN, exception.getMessage());
	            }
	        } else {
	            filterChain.doFilter(request, response);
	        }
	    }
}
