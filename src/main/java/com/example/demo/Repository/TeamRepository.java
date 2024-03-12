package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	 List<Team> findByTeamNameContainingIgnoreCase(String teamName);
	 Optional<Team> findByTeamName(String teamName);
	 
}