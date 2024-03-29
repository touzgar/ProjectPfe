package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Ressources;

public interface RessourceRepository extends JpaRepository<Ressources, Long> {

}
