package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Player;
import com.example.demo.Model.Scrims;
import com.example.demo.Model.SessionTraining;


public interface ScrimsService {
	  Scrims createScrimsWithDetails(String sessionName, String dateStart, String dateEnd, 
              String feedbacksEntraineurs, List<String> objectivesNames, 
              List<String> playerNames, String coachName, 
              String description, String niveau, String mode, List<String> specialObjectives);

	  Scrims updateScrims(Long id, Scrims scrimsDetails);
	   void deleteScrims(Scrims scrims);
	   void deleteScrimstById(Long idscrims);
	   Scrims getScrims(Long idScrims);
	   List<Scrims> getAllScrimss();
	   List<Player> getPlayersBySessionName(String sessionName);
	   SessionTraining removePlayersFromSessionByName(String sessionName, List<String> playerNames);

	    

}
