package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Player;
import com.example.demo.Model.Team;

public interface TeamService {
	Team saveTeam(Team team);
	Team UpdateTeam(Team team);
	void deleteTeam(Team team);
	void deleteTeamById(Long idTeam);
	Team getTeam(Long idClub);
	List<Team> getAllTeams();
	List<Team> searchByTeamName(String teamName);
	Team saveTeamWithClubName(Team team, String clubName);
/*	 Team saveTeamWithClubAndCoachName(Team team, String clubName, String coachName);*/
	  Team addCoachToTeam(String teamName, String coachName);
	  void removeCoachFromTeam(String teamName, String coachName);
	  Team addPlayersToTeamByNames(String teamName, List<String> playerNames);
	  Team removePlayersFromTeamByNames(String teamName, List<String> playerNames);
	  Team getTeamByName(String teamName);
	  List<Player> getPlayersByTeamNames(List<String> teamNames);

	
}
