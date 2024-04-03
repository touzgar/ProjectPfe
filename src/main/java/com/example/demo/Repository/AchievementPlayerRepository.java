package com.example.demo.Repository;

import com.example.demo.Model.AchievementPlayer;
import com.example.demo.Model.Team;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AchievementPlayerRepository extends JpaRepository<AchievementPlayer, Long> {
	Optional<AchievementPlayer> findByPlayerName(String teamName);
	List<AchievementPlayer> findByPlayer_LeagalefullnameIgnoreCase(String playerName);

    // Custom query methods can be defined here
}
