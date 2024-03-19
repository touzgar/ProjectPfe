package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Defi;
import com.example.demo.Model.Team;
import com.example.demo.Model.Tournament;
import com.example.demo.Repository.DefiRepository;

@Service
public class DefiServiceImpl implements DefiService {
	@Autowired
	DefiRepository defiRepository;
	@Override
	public Defi saveDefi(Defi defi) {
		return defiRepository.save(defi);
	}

	@Override
	public Defi updateDefi(Long idDefi, String newMatchName, LocalDateTime newDateStart, String newResult) {
	    Defi defi = defiRepository.findById(idDefi)
	            .orElseThrow(() -> new RuntimeException("Defi not found for this id :: " + idDefi));
	    
	    // Extract team names from the newMatchName
	    String[] teamNames = newMatchName.split(" vs ");
	    if (teamNames.length != 2) {
	        throw new RuntimeException("Match name must be in 'Team A vs Team B' format.");
	    }

	    // Verify both teams are registered in the tournament
	    Tournament tournament = defi.getTournament();
	    List<String> registeredTeamNames = tournament.getTeams().stream().map(Team::getTeamName).collect(Collectors.toList());
	    if (!registeredTeamNames.contains(teamNames[0]) || !registeredTeamNames.contains(teamNames[1])) {
	        throw new RuntimeException("Both teams must be registered in the tournament to update the match.");
	    }

	    // Proceed to update the defi details
	    defi.setMatchName(newMatchName);
	    defi.setDateStart(newDateStart);
	    defi.setResult(newResult);

	    return defiRepository.save(defi);
	}

	@Override
	public void deleteDefi(Defi defi) {
		defiRepository.delete(defi);
		
	}

	@Override
	public void deleteDefitById(Long iddefi) {
		defiRepository.deleteById(iddefi);
		
	}

	@Override
	public Defi getDefi(Long iddefi) {
	
		return defiRepository.findById(iddefi).get();
	}

	@Override
	public List<Defi> getAllDefis() {
		
		return defiRepository.findAll();
	}
	@Override
    public List<Defi> getHistoricalMatches() {
        LocalDateTime now = LocalDateTime.now();
        return defiRepository.findByDateStartBefore(now);
    }

}
