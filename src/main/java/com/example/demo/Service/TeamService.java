package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Team;

public interface TeamService {
	Team saveTeam(Team team);
	Team UpdateTeam(Team team);
	void deleteTeam(Team team);
	void deleteTeamById(Long idTeam);
	Team getTeam(Long idClub);
	List<Team> getAllTeams();
	List<Team> searchByTeamName(String teamName);
	List<String> findParticipatingTournamentsByTeamName(String teamName);
	Team saveTeamWithClubName(Team team, String clubName);
	 Team saveTeamWithClubAndCoachName(Team team, String clubName, String coachName);
	
	
}
