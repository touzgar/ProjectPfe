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
    User removeRoleFromUserByRoleName(Long userId, String roleName);
 // In UserService.java
    User addUserWithRole(String username, String password, String email, String roleName);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
 // In UserService.java
    User addUserWithRoleAndSendCredentials(String username, String password, String email, String roleName);
    List<User> findUsersByManagerAndAdminRoles();
    List<User> findUsersBySpecificRoles();
    List<User> findUsersByCoachRole();


   
}
