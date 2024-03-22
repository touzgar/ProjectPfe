package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.SessionTraining;

public interface SessionTrainingRepository extends JpaRepository<SessionTraining, Long> {
	 List<SessionTraining> findBysessionNameContainingIgnoreCase(String sessionName);
	 Optional<SessionTraining> findBySessionName(String sessionName);
}
