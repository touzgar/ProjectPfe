package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Defi;
import com.example.demo.Model.Team;
import com.example.demo.Model.Tournament;
import com.example.demo.Repository.DefiRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.TournamentRepository;

import jakarta.transaction.Transactional;

@Service
public class TournamentServiceImpl implements TournamentService {
	@Autowired
	TournamentRepository tournamentRepository;
	 @Autowired
	 TeamRepository teamRepository;
	 @Autowired
	 DefiRepository defiRepository;
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
    @Transactional
    public void deleteTournamentById(Long idtournament) {
        // First, get the tournament
        Tournament tournament = tournamentRepository.findById(idtournament)
                .orElseThrow(() -> new RuntimeException("Tournament not found for this id :: " + idtournament));

        // Remove associations with teams
        for (Team team : tournament.getTeams()) {
            team.getTournaments().remove(tournament);
            teamRepository.save(team); // Update the team to remove the association
        }

        // Now, it's safe to delete the tournament
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

	    // Check if adding these teams will exceed the tournament's capacity
	    if (tournament.getTeams().size() + teamNames.size() > tournament.getCapacity()) {
	        throw new RuntimeException("Cannot add more teams. Tournament capacity exceeded.");
	    }

	    for (String teamName : teamNames) {
	        Team team = teamRepository.findByTeamName(teamName)
	                .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));

	        // Assuming you want to avoid adding duplicate teams
	        if (!tournament.getTeams().contains(team)) {
	            tournament.getTeams().add(team);
	            team.getTournaments().add(tournament);
	        }
	    }

	    return tournamentRepository.save(tournament);
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
	  public boolean tournamentNameExists(String tournamentName) {
	        return tournamentRepository.findByTournamentName(tournamentName).isPresent();
	    }
	  @Override
	  public Tournament addMatchAndEnsureTeamRegistration(String tournamentName, String matchDescription, Date matchDateTime) {
	        // Extract team names from the match description
	        String[] teamNames = matchDescription.split(" vs ");
	        if (teamNames.length != 2) {
	            throw new IllegalArgumentException("Match description must be in the format 'Team1 vs Team2'.");
	        }

	        // Fetch the tournament
	        Tournament tournament = tournamentRepository.findByTournamentName(tournamentName)
	                .orElseThrow(() -> new RuntimeException("Tournament not found: " + tournamentName));

	        // Verify both teams are registered in the tournament
	        for (String teamName : teamNames) {
	            Team team = teamRepository.findByTeamName(teamName)
	                    .orElseThrow(() -> new RuntimeException("Team not found: " + teamName));

	            if (!tournament.getTeams().contains(team)) {
	                throw new IllegalArgumentException("Team " + teamName + " is not registered in the tournament.");
	            }
	        }

	        // Proceed to add the match since both teams are registered
	        Defi newMatch = new Defi();
	        newMatch.setMatchName(matchDescription); // Storing team names in the match name
	        newMatch.setDateStart(matchDateTime); // Now using Date
	        newMatch.setResult(""); // Assuming the result is initially empty
	        newMatch.setTournament(tournament);
	        defiRepository.save(newMatch);

	        return tournament;
	    }	
	  
	  public void deleteMatchFromTournament(Long idDefi) {
		    Defi defi = defiRepository.findById(idDefi)
		            .orElseThrow(() -> new RuntimeException("Defi not found for this id :: " + idDefi));
		    
		    Tournament tournament = defi.getTournament();
		    if(tournament != null) {
		        tournament.getDefi().remove(defi); // Remove the match from the tournament
		        // Assuming you have a tournamentRepository or similar
		        tournamentRepository.save(tournament); // Save the tournament to update the association
		    }
		    defiRepository.delete(defi); // Finally, delete the defi itself
		}
	  @Override
	  public List<Tournament> getHistoricalTournaments() {
	      LocalDateTime now = LocalDateTime.now();
	      return tournamentRepository.findByDateEndBefore(now);
	  }
	  @Override
		public List<Tournament> searchByTournamentName(String tournamentName) {
		    return tournamentRepository.findByTournamentNameContainingIgnoreCase(tournamentName);
		}


}
