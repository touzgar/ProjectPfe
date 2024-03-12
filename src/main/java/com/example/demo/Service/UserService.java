package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Service.register.RegistrationRequest;

public interface UserService {
	User saveUser(User user);
	User findUserByUsername(String username);
	Role addRole(Role role);
	User addRoleToUser(String username,String roleName);
	List<User> findAllUsers();
	User registerUser(RegistrationRequest request);
	public void sendEmailUser(User u,String code);
	public User validateToken(String code);
}
