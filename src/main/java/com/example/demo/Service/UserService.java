package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Service.register.RegistrationRequest;

public interface UserService {
	User saveUser(User user);
	User findUserById(Long id);
	User findUserByUsername(String username);
	Role addRole(Role role);
	User addRoleToUser(String username,String roleName);
	List<User> findAllUsers();
	User registerUser(RegistrationRequest request);
	public void sendEmailUser(User u,String code);
	public User validateToken(String code);
	void createPasswordResetTokenForUser(User user, String token);
    void changeUserPassword(User user, String newPassword);
    void deleteUser(long id);
    List<Role> findAllRoles();
    Role findRoleById(Long id);
    User removeRoleFromUser(long id, Role r);
}
