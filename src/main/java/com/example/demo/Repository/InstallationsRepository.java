package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Installation;

public interface InstallationsRepository extends JpaRepository<Installation, Long> {

}
