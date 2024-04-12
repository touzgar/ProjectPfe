package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 User findByUsername(String username); 
	 Optional<User> findByEmail(String email);
	 @Query("select u from User u where u.userId = ?1")
	 User findUserById(Long id);

	 @Modifying
	 @Query("delete from User u where u.userId = :userId")
	 void deleteByUserId(@Param("userId") Long userId);
	 Optional<User> findById(Long id);
	 Optional<User> searchByUsername(String username);
	// In UserRepository.java
	 @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role IN :roles")
	 List<User> findUsersByRoleNames(@Param("roles") List<String> roles);
	 @Query("SELECT u FROM User u JOIN u.roles r WHERE r.role = 'COACH'")
	    List<User> findUsersWithRoleCoach();
	 
}
