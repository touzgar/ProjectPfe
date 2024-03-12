package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
