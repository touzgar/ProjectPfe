package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
	Optional<Tournament> findByTournamentName(String tournamentName);
}
