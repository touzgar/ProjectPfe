package com.example.demo.Controller;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.PasswordResetToken;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.PasswordResetTokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.Service.register.RegistrationRequest;
import com.example.demo.security.SecParams;
import com.example.demo.security.TokenStore;

@RestController
@CrossOrigin("*")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
    private TokenStore tokenStore;
	@Autowired 
	UserRepository userRepository;
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	
	@PostMapping("/register")
	public User register(@RequestBody RegistrationRequest request) {
		return userService.registerUser(request);
	}
	
	@GetMapping("/verifyEmail/{token}")
	public User verifyEmail(@PathVariable("token") String token) {
		return userService.validateToken(token);
	}
	
	 
	    @PostMapping("/logout")
	    public ResponseEntity<?> logout(@RequestHeader(value="Authorization") String authHeader) {
	        if (authHeader != null && authHeader.startsWith(SecParams.PREFIX)) {
	            String token = authHeader.replace(SecParams.PREFIX, "");
	            tokenStore.blacklistToken(token, new Date(System.currentTimeMillis() + SecParams.EXP_TIME));
	            return ResponseEntity.ok().body("You've been logged out successfully.");
	        }
	        return ResponseEntity.badRequest().body("Invalid token.");
	    }
	    @PostMapping("/forgot-password")
	    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
	        Optional<User> userOptional = userRepository.findByEmail(email);
	        if (!userOptional.isPresent()) {
	            return new ResponseEntity<>("User not found with email: " + email, HttpStatus.NOT_FOUND);
	        }

	        User user = userOptional.get(); // Get the user from the Optional
	        String token = UUID.randomUUID().toString();
	        userService.createPasswordResetTokenForUser(user, token);

	        return new ResponseEntity<>("Password reset email sent", HttpStatus.OK);
	    }

	    @PostMapping("/reset-password")
	    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
	                                           @RequestParam("password") String newPassword) {
	        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
	        if (resetToken == null || resetToken.isExpired()) {
	            return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
	        }

	        User user = resetToken.getUser();
	        userService.changeUserPassword(user, newPassword);
	        passwordResetTokenRepository.delete(resetToken); // Invalidate the token after use
	        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
	    }
	    @RequestMapping(path = "all", method = RequestMethod.GET)
	    public List<User> getAllUsers() {
	        return userService.findAllUsers();
	    }
	  

	    @RequestMapping(path="/deleteUser/{id}",method=RequestMethod.DELETE)
	    
	    public void deleteUserById(@PathVariable long id) {
	        userService.deleteUser(id);
	    }

	   
	    @PostMapping("/addRoleToUser/{username}")
	    public ResponseEntity<User> addRoleToUser(@PathVariable String username, @RequestBody Role role) {
	        try {
	            User user = userService.addRoleToUser(username, role.getRole()); // Assuming getRole() returns the role name
	            return ResponseEntity.ok(user);
	        } catch (Exception e) {
	            // Handle your exceptions here, like UserNotFoundException or RoleNotFoundException
	            return ResponseEntity.badRequest().build();
	        }
	    }

	   

	    
	    @RequestMapping(path="allRoles",method=RequestMethod.GET)
	    public List<Role> getAllRoles() {
	        return userService.findAllRoles();
	    }


	    @RequestMapping(path="role/{id}",method=RequestMethod.GET)
	    public Role findRoleById(@PathVariable Long id) {
	        return userService.findRoleById(id);
	    }
	    @RequestMapping(path="removeRole/{id}",method=RequestMethod.POST)
	    public User removeRole(@PathVariable long id,@RequestBody Role r)
	    {
	        return  userService.removeRoleFromUser(id,r);
	    }
	    @PostMapping("/addRole")
	    public ResponseEntity<Role> addRole(@RequestBody Role role) {
	        Role savedRole = userService.addRole(role);
	        return ResponseEntity.ok(savedRole);
	    }



}
