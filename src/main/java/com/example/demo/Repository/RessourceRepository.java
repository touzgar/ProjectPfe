package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Installation;
import com.example.demo.Model.Ressources;

public interface RessourceRepository extends JpaRepository<Ressources, Long> {

	Optional<Ressources> findByResourceName(String resourceName);
	List<Ressources> findByResourceNameContainingIgnoreCase(String resourceName);


}
