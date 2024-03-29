package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.example.demo.Model.Defi;
import com.example.demo.Model.Tournament;

public interface TournamentService {
	Tournament saveTournament(Tournament tournament);
	Tournament UpdateTournament(Tournament tournament);
	void deleteTournament(Tournament tournament);
	void deleteTournamentById(Long idtournament);
	Tournament getTournament(Long idtournament);
	List<Tournament> getAllTournaments();
	Tournament registerTeamsInTournament(String tournamentName, List<String> teamNames);
	Tournament removeTeamsFromTournament(String tournamentName, List<String> teamNames);
	boolean tournamentNameExists(String tournamentName);
	Tournament addMatchAndEnsureTeamRegistration(String tournamentName, String matchDescription, Date matchDateTime);
	void deleteMatchFromTournament(Long idDefi);
	 List<Tournament> getHistoricalTournaments();
	 List<Tournament> searchByTournamentName(String tournamentName);
}
