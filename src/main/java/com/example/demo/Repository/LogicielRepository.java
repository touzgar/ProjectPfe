package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Logiciel;

public interface LogicielRepository extends JpaRepository<Logiciel, Long> {
	List<Logiciel> findByLogicielNameContainingIgnoreCase(String logicielName);

}
