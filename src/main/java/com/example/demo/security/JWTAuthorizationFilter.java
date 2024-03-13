package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
	                // Further authentication and authorization logic...
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
