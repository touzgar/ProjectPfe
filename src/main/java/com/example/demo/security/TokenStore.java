package com.example.demo.security;

	import java.util.Date;
	import java.util.concurrent.ConcurrentHashMap;

	public class TokenStore {
	    private static final ConcurrentHashMap<String, Date> tokenBlacklist = new ConcurrentHashMap<>();

	    public void blacklistToken(String token, Date expiryDate) {
	        tokenBlacklist.put(token, expiryDate);
	    }

	    public boolean isTokenBlacklisted(String token) {
	        Date expiryDate = tokenBlacklist.getOrDefault(token, null);
	        if (expiryDate == null) {
	            return false;
	        }
	        return expiryDate.after(new Date());
	    }
	}


