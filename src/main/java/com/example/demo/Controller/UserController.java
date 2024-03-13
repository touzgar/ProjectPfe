package com.example.demo.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.Service.register.RegistrationRequest;

@RestController
@CrossOrigin("*")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("all")
	public List<User> getAllUsers(){
		return userService.findAllUsers();
	}
	@PostMapping("/register")
	public User register(@RequestBody RegistrationRequest request) {
		return userService.registerUser(request);
	}
	
	@GetMapping("/verifyEmail/{token}")
	public User verifyEmail(@PathVariable("token") String token) {
		return userService.validateToken(token);
	}
	
		

}
