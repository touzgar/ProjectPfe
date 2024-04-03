package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.AchivementTeam;
;

public interface AchivementTeamService {
	AchivementTeam saveAchivementTeam(AchivementTeam achivementTeam);
	AchivementTeam UpdateAchivementTeam(AchivementTeam achivementTeam);
	void deleteAchivementTeam(AchivementTeam achivementTeam);
	void deleteAchivementTeamById(Long idAchivementTeam);
	AchivementTeam getAchivementTeam(Long idAchivementTeam);
	List<AchivementTeam> getAllAchivementTeams();
	AchivementTeam saveAchievementWithTeam(AchivementTeam achivementTeam, String teamName); // New method


}
