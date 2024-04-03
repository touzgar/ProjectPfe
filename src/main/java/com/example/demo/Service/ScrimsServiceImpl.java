package com.example.demo.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Coach;
import com.example.demo.Model.Player;
import com.example.demo.Model.Scrims;
import com.example.demo.Model.SessionTraining;
import com.example.demo.Repository.CoachRepository;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Repository.ScrimsRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ScrimsServiceImpl implements ScrimsService {
	@Autowired
	ScrimsRepository scrimsRepository;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	CoachRepository coachRepository;
	 @Override
	    @Transactional
	    public Scrims createScrimsWithDetails(String sessionName, String dateStringStart, String dateStringEnd, 
	                                          String feedbacksEntraineurs, List<String> objectivesNames, 
	                                          List<String> playerNames, String coachName, 
	                                          String description, String niveau, String mode, List<String> specialObjectives) {
	        // Parsing string dates to Date objects
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        Date dateStart = null;
	        Date dateEnd = null;
	        try {
	            dateStart = dateFormat.parse(dateStringStart);
	            dateEnd = dateFormat.parse(dateStringEnd);
	        } catch (ParseException e) {
	            throw new RuntimeException("Error parsing date fields: " + e.getMessage());
	        }

	        Coach coach = coachRepository.findByNameCoachIgnoreCase(coachName)
	                .orElseThrow(() -> new EntityNotFoundException("Coach not found with name: " + coachName));

	        List<Player> players = playerRepository.findByLeagalefullnameInIgnoreCase(playerNames);
	        if (players.isEmpty()) {
	            throw new EntityNotFoundException("One or more players not found by names: " + playerNames);
	        }

	        Scrims scrims = new Scrims();
	        scrims.setSessionName(sessionName);
	        scrims.setDateStart(dateStart);
	        scrims.setDateEnd(dateEnd);
	        scrims.setFeedbacksEntraineurs(feedbacksEntraineurs);
	        scrims.setObjectifs(objectivesNames); // Ensure your entity is set up to handle List<String>
	        scrims.setDescription(description);
	        scrims.setNiveau(niveau);
	        scrims.setMode(mode);
	        scrims.setSpecialObjectives(specialObjectives); // Ensure your entity is set up to handle List<String>
	        scrims.setScrimsPlayers(players);
	        scrims.setCoach(coach);

	        return scrimsRepository.save(scrims);
	    }	  @Override
	    @Transactional
	    public Scrims updateScrims(Long id, Scrims scrimsDetails) {
	        Scrims existingScrims = scrimsRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Scrims not found with id: " + id));

	        // Update properties
	        existingScrims.setDescription(scrimsDetails.getDescription());
	        existingScrims.setNiveau(scrimsDetails.getNiveau());
	        existingScrims.setMode(scrimsDetails.getMode());
	        existingScrims.setScrimsPlayers(scrimsDetails.getScrimsPlayers());
	        // Assuming setters for inherited properties and relationships are correctly implemented

	        return scrimsRepository.save(existingScrims);
	    }		@Override
		public void deleteScrims(Scrims scrims) {
			scrimsRepository.delete(scrims);
		}

		@Override
		public void deleteScrimstById(Long idscrims) {
			scrimsRepository.deleteById(idscrims);
			
		}

		@Override
		public Scrims getScrims(Long idScrims) {
			
			return scrimsRepository.findById(idScrims).get();
		}

		@Override
		public List<Scrims> getAllScrimss() {
		
			return scrimsRepository.findAll();
		}
		
		  @Override
		    public List<Player> getPlayersBySessionName(String sessionName) {
		        Scrims scrims = scrimsRepository.findBySessionName(sessionName)
		                .orElseThrow(() -> new EntityNotFoundException("Session not found with name: " + sessionName));
		        return scrims.getScrimsPlayers();
		    }
		  
		  @Override
		    @Transactional
		    public SessionTraining removePlayersFromSessionByName(String sessionName, List<String> playerNames) {
		        Scrims scrims = scrimsRepository.findBySessionName(sessionName)
		                .orElseThrow(() -> new EntityNotFoundException("SessionTraining not found with name: " + sessionName));
		        
		        List<Player> playersToRemove = playerRepository.findByLeagalefullnameInIgnoreCase(playerNames);
		        if (playersToRemove.isEmpty()) {
		            throw new EntityNotFoundException("Some or all players not found");
		        }

		        scrims.getScrimsPlayers().removeAll(playersToRemove);
		        return scrimsRepository.save(scrims);
		    }

		  
		}
