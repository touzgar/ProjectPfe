package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.AchivementsTeam;

public interface AchivementTeamService {
	AchivementsTeam saveAchivementsTeam(AchivementsTeam achivement);
	AchivementsTeam updateAchivementsTeam(Long achivementId, AchivementsTeam achivementDetails);
	void deleteAchivementsTeam(AchivementsTeam achivement);
	void deleteAchivementsTeamById(Long idachivement);
	AchivementsTeam getAchivementsTeam(Long idachivement);
	List<AchivementsTeam> getAllAchivementsTeams();
	
}