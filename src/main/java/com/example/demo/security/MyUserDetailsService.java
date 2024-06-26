package com.example.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserService userService; 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =userService.findUserByUsername(username);//chercher Username
		if(user==null)//Condition of the user not found
			throw new UsernameNotFoundException("Utilisateur introuvable!");
		List<GrantedAuthority> auths = new ArrayList<>();//declaration de GrantedAuthority 
		user.getRoles().forEach(role -> {
		    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRole());
		    auths.add(authority);
		});

		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getEnabled(),true,true,true,auths);
	}

}
