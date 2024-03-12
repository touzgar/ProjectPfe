package com.example.demo.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long userId;
@Column(unique=true)
private String username;
private String password;
private Boolean enabled;//si user active ou inactive
private String email;

@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)///chaque user possede une list de role
@JoinTable(name="user_role",joinColumns = @JoinColumn(name="userId"),
		inverseJoinColumns = @JoinColumn(name="roleId"))
private List<Role>roles;





}
