package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Materiel;

public interface MaterielRepository extends JpaRepository<Materiel, Long> {

}
