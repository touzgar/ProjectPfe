package com.example.demo.Service;

import com.example.demo.Model.AchievementPlayer;
import com.example.demo.Model.Team;

import java.util.List;

public interface AchievementPlayerService {
	AchievementPlayer createAchievementPlayer(AchievementPlayer achievementPlayer);
	AchievementPlayer updateAchievementPlayer(AchievementPlayer achievementPlayer);
    void deleteAchievementPlayerById(Long id);
    AchievementPlayer getAchievementPlayerById(Long id);
    List<AchievementPlayer> getAllAchievementPlayers();
    List<AchievementPlayer> searchByPlayerName(String playerName);
}
