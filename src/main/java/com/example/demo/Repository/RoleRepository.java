package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);
	 List<Role> findAll();
	 @Query("select r from Role r where r.roleId = ?1")
	 Role findRoleById(Long id);

}
