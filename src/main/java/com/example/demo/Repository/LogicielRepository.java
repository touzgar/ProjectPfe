package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Logiciel;

public interface LogicielRepository extends JpaRepository<Logiciel, Long> {

}
