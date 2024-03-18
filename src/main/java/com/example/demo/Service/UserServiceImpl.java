package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.PasswordResetToken;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
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
		userRepository.deleteAll();
		
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
	        return user;}
	  }
