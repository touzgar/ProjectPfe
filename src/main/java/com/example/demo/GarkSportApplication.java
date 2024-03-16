package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Service.UserService;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class GarkSportApplication {

    @Autowired
    UserService userService;
   /*@PostConstruct
    void init_users() {
//ajouter les rôles
        userService.addRole(new Role(null,"ADMIN"));
        userService.addRole(new Role(null,"USER"));
        userService.addRole(new Role(null,"MANAGER"));
        
//ajouter les users
        userService.saveUser(new User(null,"admin","123",true,"ghaithslama26@gmail.com", null));
        userService.saveUser(new User(null,"ahmed","123",true,"ghaithslama115@gmail.com", null));
        userService.saveUser(new User(null,"ghaith","123",true,"ghaithslama115@gmail.com", null));
        userService.saveUser(new User(null,"khalil","123",true,"ghaithslama115@gmail.com", null));
//ajouter les rôles aux users
        userService.addRoleToUser("admin", "ADMIN");
        userService.addRoleToUser("admin", "USER");
        userService.addRoleToUser("ahmed", "USER");
        userService.addRoleToUser("ghaith", "USER");
        userService.addRoleToUser("khalil", "MANAGER");
        
    }
   
	
	*/
	
    public static void main(String[] args) {
        SpringApplication.run(GarkSportApplication.class, args);
        System.out.println("YOKOSO WATASHO NO SOUL SOCIETY HADO NO 99");
    }

   }
