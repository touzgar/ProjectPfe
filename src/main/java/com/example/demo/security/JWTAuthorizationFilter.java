package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    String jwtToken = request.getHeader("Authorization");
	    if (jwtToken != null && jwtToken.startsWith(SecParams.PREFIX)) {
	        try {
	            jwtToken = jwtToken.replace(SecParams.PREFIX, "");
	            Algorithm algorithm = Algorithm.HMAC256(SecParams.SECRET); // Use the same secret key
	            JWTVerifier verifier = JWT.require(algorithm).build();
	            DecodedJWT decodedJWT = verifier.verify(jwtToken); // Verify the token
	            String username = decodedJWT.getSubject();
	            // Proceed with authentication
	        } catch (JWTVerificationException exception) {
	            // Log and handle error
	            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Error: " + exception.getMessage());
	            return;
	        }
	    }
	    filterChain.doFilter(request, response);
	}


}
