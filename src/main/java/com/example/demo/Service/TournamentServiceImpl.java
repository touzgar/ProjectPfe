package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Team;
import com.example.demo.Model.Tournament;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.TournamentRepository;

@Service
public class TournamentServiceImpl implements TournamentService {
	@Autowired
	TournamentRepository tournamentRepository;
	 @Autowired
	    private TeamRepository teamRepository;
	@Override
	public Tournament saveTournament(Tournament tournament) {
		
		return tournamentRepository.save(tournament);
	}

	@Override
	public Tournament UpdateTournament(Tournament tournament) {
		return tournamentRepository.save(tournament);
	}

	@Override
	public void deleteTournament(Tournament tournament) {
	tournamentRepository.delete(tournament);
		
	}

	@Override
	public void deleteTournamentById(Long idtournament) {
		tournamentRepository.deleteById(idtournament);
		
	}

	@Override
	public Tournament getTournament(Long idtournament) {
		
		return tournamentRepository.findById(idtournament).get();
	}

	@Override
	public List<Tournament> getAllTournaments() {
		
		return tournamentRepository.findAll();
	}
	@Override
	public Tournament registerTeamsInTournament(String tournamentName, List<String> teamNames) {
	    Tournament tournament = tournamentRepository.findByTournamentName(tournamentName)
	            .orElseThrow(() -> new RuntimeException("Tournament with name '" + tournamentName + "' not found"));

	    // Fetch all teams in the tournament once to avoid querying inside the loop
	    List<Team> existingTeams = tournament.getTeams();

	    List<String> alreadyRegistered = new ArrayList<>();

	    for (String teamName : teamNames) {
	        Team team = teamRepository.findByTeamName(teamName)
	                .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));

	        // Check if the team is already registered in the tournament
	        if (existingTeams.contains(team)) {
	            alreadyRegistered.add(teamName); // Collect names of already registered teams
	            continue; // Skip adding this team
	        }

	        tournament.getTeams().add(team); // Add the team to the tournament
	        team.getTournaments().add(tournament); // This might not be necessary depending on your CascadeType settings
	    }

	    Tournament updatedTournament = tournamentRepository.save(tournament);

	    // Handle already registered teams as needed
	    if (!alreadyRegistered.isEmpty()) {
	        String message = "Some teams are already registered and were not added again: " + String.join(", ", alreadyRegistered);
	        // You might want to log this message or handle it differently
	        System.out.println(message);
	    }

	    return updatedTournament;
	}
	  @Override
	  public Tournament removeTeamsFromTournament(String tournamentName, List<String> teamNames) {
	      Tournament tournament = tournamentRepository.findByTournamentName(tournamentName)
	              .orElseThrow(() -> new RuntimeException("Tournament with name '" + tournamentName + "' not found"));
	      List<Team> teamsToRemove = teamNames.stream()
	              .map(teamName -> teamRepository.findByTeamName(teamName)
	                      .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found")))
	              .collect(Collectors.toList());

	      tournament.getTeams().removeAll(teamsToRemove);
	      teamsToRemove.forEach(team -> team.getTournaments().remove(tournament));

	      return tournamentRepository.save(tournament);
	  }

}
