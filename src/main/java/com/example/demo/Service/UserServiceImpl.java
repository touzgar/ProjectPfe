package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Image;
import com.example.demo.Model.PasswordResetToken;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.PasswordResetTokenRepository;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.execption.EmailAlreadyExistsException;
import com.example.demo.Service.execption.ExpiredTokenException;
import com.example.demo.Service.execption.InvalidTokenException;
import com.example.demo.Service.register.RegistrationRequest;
import com.example.demo.Service.register.VerificationToken;
import com.example.demo.Service.register.VerificationTokenRepository;
import com.example.demo.util.EmailSender;
@Transactional
@Service
public class UserServiceImpl implements UserService {
@Autowired
UserRepository userRepository;
@Autowired
RoleRepository roleRepository;
@Autowired
BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired
VerificationTokenRepository verificationTokenRepository;
@Autowired
EmailSender emailSender;
@Autowired
private PasswordResetTokenRepository passwordResetTokenRepository;
@Autowired
ImageRepository imageRepository;

	@Override
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));////pour crypte le password
		return userRepository.save(user);
	}

	@Override
	public User findUserByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public Role addRole(Role role) {
		
		return roleRepository.save(role);
	}

	@Override
	public User addRoleToUser(String username, String roleName) {
		User usr=userRepository.findByUsername(username);
		Role role=roleRepository.findByRole(roleName);
		usr.getRoles().add(role);
		
		return usr;
	}

	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public User registerUser(RegistrationRequest request) {
			
		Optional<User> optionalUser=userRepository.findByEmail(request.getEmail());
		if(optionalUser.isPresent()) 
			throw new EmailAlreadyExistsException("Email deja existant !"); 
		
		User newUser = new User();
		newUser.setUsername(request.getUsername());
		newUser.setEmail(request.getEmail());
		newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		newUser.setEnabled(false);
		
		userRepository.save(newUser);
		Role r = roleRepository.findByRole("User");
		List<Role> roles = new ArrayList<>();
		roles.add(r);
		newUser.setRoles(roles);
		
		//genere le code secret
		String code =this.generateCode();
		
		VerificationToken token=new VerificationToken(code,newUser);
		verificationTokenRepository.save(token);
		
		//envoyer le code par email a l'utilisateur
		sendEmailUser(newUser,token.getToken());
		
		
		return userRepository.save(newUser);
	}

	private String generateCode() {
		Random random = new Random();
		Integer code =100000 + random.nextInt(900000);
		return code.toString();
	
	
	}
	@Override
	public void sendEmailUser(User u,String code) {
		String emailBody ="Bonjour" + "<h1>"+u.getUsername()+"</h1>" + "Votre code de validation est "+"<h1>"+code+"</h1>";
		emailSender.sendEmail(u.getEmail(),emailBody);
	}
	@Override
	public User validateToken(String code) {
		VerificationToken token=verificationTokenRepository.findByToken(code);
		if(token == null) {
			throw new InvalidTokenException ("Invalid Token");
		}
		User user =token.getUser();
		Calendar calendar = Calendar.getInstance();
		if((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
				verificationTokenRepository.delete(token);
				throw new ExpiredTokenException("Expired Token");
				
		}
		user.setEnabled(true);
		userRepository.save(user);
		return user;
	}
	  @Override
	    public void createPasswordResetTokenForUser(User user, String token) {
	        PasswordResetToken myToken = new PasswordResetToken();
	        myToken.setUser(user);
	        myToken.setToken(token);
	        myToken.setExpiryDate(60); // token expires in 60 minutes
	        passwordResetTokenRepository.save(myToken);

	        sendPasswordResetEmail(user, token);
	    }
	  @Override
	    public void changeUserPassword(User user, String newPassword) {
	        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
	        userRepository.save(user);
	    }
	  private void sendPasswordResetEmail(User user, String token) {
		    String recipientAddress = user.getEmail();
		    String subject = "Password Reset Request";
		    String resetUrl = "http://localhost:8089/users/reset-password?token=" + token;
		    String emailBody = "To reset your password, click the link below:\n" + resetUrl;

		    emailSender.sendEmail(recipientAddress, emailBody); // Corrected method call
		}

	@Override
	public User findUserById(Long id) {
	
		return userRepository.findById(id).get();
	}

	@Override
	public void deleteUser(long id) {
	    // Check if the user exists in the database
	    Optional<User> userOptional = userRepository.findById(id);
	    if (!userOptional.isPresent()) {
	        throw new RuntimeException("User not found with id: " + id); // You can create a custom exception if you prefer
	    }
	    // If the user exists, delete the user by ID
	    userRepository.deleteById(id);
	}


	@Override
	public List<Role> findAllRoles() {
		
		return roleRepository.findAll();
	}

	@Override
	public Role findRoleById(Long id) {
		
		return roleRepository.findRoleById(id);
	}

	@Override
	public User removeRoleFromUser(long id, Role r) {
		 User user = userRepository.findUserById(id);
	        List<Role> listOfRoles = user.getRoles();
	        listOfRoles.remove(r);
	        userRepository.save(user);
	        return user;
	        
	}
	@Override
	public User removeRoleFromUserByRoleName(Long userId, String roleName) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
	    Role role = roleRepository.searchByRole(roleName)
	            .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));

	    user.getRoles().removeIf(r -> r.getRole().equals(roleName));
	    return userRepository.save(user);
	}
	// In UserServiceImpl.java
	@Override
    public User addUserWithRole (String username, String password, String email, String roleName) {
        if (userRepository.searchByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists!");
        }
        if (roleName == null || (!roleName.trim().equalsIgnoreCase("manager") && !roleName.trim().equalsIgnoreCase("admin"))) {
            throw new RuntimeException("Role must be either 'manager' or 'admin'");
        }

        Role role = roleRepository.findByRole(roleName);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        User newUser = new User();
       
        
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password)); // Encrypting password
        newUser.setRoles(Collections.singletonList(role));
        newUser.setEnabled(true);

        User savedUser = userRepository.save(newUser);

        // Send email with credentials
        sendCredentialsEmail(newUser.getEmail(), newUser.getUsername(), password);

        return savedUser;
    }

    private void sendCredentialsEmail(String email, String username, String password) {
        String subject = "Your Account Credentials";
        String emailBody = "Welcome! Your account has been created. \nUsername: " + username +
                "\nPassword: " + password +
                "\nPlease change your password upon your first login.";

        emailSender.emailSend(email, subject, emailBody);
    }
	 @Override
	 public User updateUser(Long userId, User userDetails) {
	     User user = userRepository.findById(userId)
	             .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

	     user.setUsername(userDetails.getUsername());
	     user.setEmail(userDetails.getEmail());
	     user.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
	     user.setEnabled(userDetails.getEnabled());
	     // Update other fields as necessary

	     return userRepository.save(user);
	 }

	 @Override
	 public void deleteUser(Long userId) {
	     User user = userRepository.findById(userId)
	         .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

	     // Clear the roles associated with the user to avoid foreign key constraint failure
	     user.getRoles().clear();
	     userRepository.save(user); // Save the user to update the relationship in the database

	     userRepository.deleteById(userId);
	 }
	// In UserServiceImpl.java
	 @Override
	 public User addUserWithRoleAndSendCredentials(String username, String password, String email, String roleName) {
		 if (userRepository.searchByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
	            throw new RuntimeException("User already exists!");
	        }
	     
		 if (roleName == null || (!roleName.trim().equalsIgnoreCase("coach") && !roleName.trim().equalsIgnoreCase("player")&&!roleName.trim().equalsIgnoreCase("sponsor"))) {
	            throw new RuntimeException("Role must be either 'coach' or 'player' or 'sponsor'");
	        }
	     
		 Role role = roleRepository.findByRole(roleName);
	        if (role == null) {
	            throw new RuntimeException("Role not found");
	        }
	     
	     User newUser = new User();
	     newUser.setUsername(username);
	     newUser.setPassword(bCryptPasswordEncoder.encode(password));
	     newUser.setEmail(email);
	     newUser.setEnabled(true);
	     newUser.setRoles(List.of(role));
	     
	     userRepository.save(newUser);
	     
	     // Send email
	     sendCredentialsEmail(email, username, password);
	     
	     return newUser;
	 }
	// In UserServiceImpl.java
	 @Override
	 public List<User> findUsersByManagerAndAdminRoles() {
	     List<String> specificRoles = List.of("MANAGER", "ADMIN");
	     List<User> allUsers = findAllUsers();
	     return allUsers.stream()
	             .filter(user -> user.getRoles().stream()
	                     .anyMatch(role -> specificRoles.contains(role.getRole().toUpperCase())))
	             .collect(Collectors.toList());
	 }
	 @Override
	 public List<User> findUsersBySpecificRoles() {
	     List<String> specificRoles = List.of("COACH", "SPONSOR", "PLAYER");
	     List<User> allUsers = findAllUsers();
	     return allUsers.stream()
	             .filter(user -> user.getRoles().stream()
	                     .anyMatch(role -> specificRoles.contains(role.getRole().toUpperCase())))
	             .collect(Collectors.toList());
	 }

	
	 
	  }
