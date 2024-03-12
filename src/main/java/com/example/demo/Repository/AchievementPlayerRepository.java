package com.example.demo.Repository;

import com.example.demo.Model.AchievementPlayer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AchievementPlayerRepository extends JpaRepository<AchievementPlayer, Long> {
    // Custom query methods can be defined here
}
