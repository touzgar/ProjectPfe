package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Installation;

public interface InstallationsRepository extends JpaRepository<Installation, Long> {
	 List<Installation> findByInstallationNameContainingIgnoreCase(String installationName);

}
