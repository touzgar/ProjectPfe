package com.example.demo.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Club;
import com.example.demo.Model.Player;
import com.example.demo.Model.Scrims;
import com.example.demo.Model.SessionTraining;
import com.example.demo.Service.ScrimsService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/scrims")
@CrossOrigin("*")
public class ScrimsController {
	@Autowired
    private ScrimsService scrimsService;
	 @PostMapping("/add")
	    public ResponseEntity<?> addScrimsWithDetails(@RequestBody Map<String, Object> payload) {
	        try {
	            String sessionName = (String) payload.get("sessionName");
	            String dateStart = (String) payload.get("dateStart");
	            String dateEnd = (String) payload.get("dateEnd");
	            String feedbacksEntraineurs = (String) payload.get("feedbacksEntraineurs");
	            List<String> objectivesNames = (List<String>) payload.get("objectivesNames");
	            List<String> playerNames = (List<String>) payload.get("playerNames");
	            String coachName = (String) payload.get("coachName");
	            String description = (String) payload.get("description");
	            String niveau = (String) payload.get("niveau");
	            String mode = (String) payload.get("mode");
	            List<String> specialObjectives = (List<String>) payload.get("specialObjectives");

	            Scrims savedScrims = scrimsService.createScrimsWithDetails(sessionName, dateStart, dateEnd, 
	                                                                       feedbacksEntraineurs, objectivesNames, 
	                                                                       playerNames, coachName, description, 
	                                                                       niveau, mode, specialObjectives);
	            return ResponseEntity.ok(savedScrims);
	        } catch (DateTimeParseException e) {
	            return ResponseEntity.badRequest().body("Error parsing date fields: " + e.getMessage());
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
	        }
	    }
	 
	 
	 
	 
	 @GetMapping("/getAll")
	List<Scrims> getAlScrims(){
		return scrimsService.getAllScrimss();
	}
	 @GetMapping("/get/{id}")
	public Scrims getScrimsById(@PathVariable("id") Long id) {
		return scrimsService.getScrims(id);
	}
	/*  @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateScrims(@PathVariable Long id, @RequestBody Scrims scrims) {
		    
	        try {
	            Scrims updatedScrims = scrimsService.updateScrims(id, scrims);
	            return ResponseEntity.ok(updatedScrims);
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
	        }
	    } 
	  */
	  
	  @PutMapping("/update/{id}")
		 public ResponseEntity<?> updateScrims(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
		        try {
		            Scrims existingScrims = scrimsService.getScrims(id);
		            if (existingScrims == null) {
		                return ResponseEntity.notFound().build();
		            }

		            // Update player details from payload
		            String description = (String) payload.get("description");
		            if (description != null) existingScrims.setDescription(description);
		            
		            String mode = (String) payload.get("mode");
		            if (mode != null) existingScrims.setMode(mode);
		            
		            String niveau = (String) payload.get("niveau");
		            if (niveau != null) existingScrims.setNiveau(niveau);
		            
		            List<String> specialObjectives = (List<String>) payload.get("specialObjectives");
		            if(specialObjectives!=null)existingScrims.setSpecialObjectives(specialObjectives);
		            
		            List<String> playerNames = (List<String>) payload.get("playerNames");
		            
		            
		            
		            
		          	            	// Now, save the updated player information
		            Scrims updatedScrims = scrimsService.updateScrims(id, existingScrims);

		            return ResponseEntity.ok(updatedScrims);
		        } catch (Exception e) {
		            return ResponseEntity.badRequest().body("An error occurred while updating the Scrims: " + e.getMessage());
		        }
		    }

	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	 @DeleteMapping("/delete/{id}")
		public void deleteScrims(@PathVariable("id") Long id) {
		    	scrimsService.deleteScrimstById(id);
		}
	 @GetMapping("/getPlayersBySession/{sessionName}")
	    public ResponseEntity<?> getPlayersBySessionName(@PathVariable String sessionName) {
	        try {
	            List<Player> players = scrimsService.getPlayersBySessionName(sessionName);
	            return ResponseEntity.ok(players);
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error retrieving players: " + e.getMessage());
	        }
	    }
	 
	  @DeleteMapping("/removePlayersByName/{sessionName}")
	    public ResponseEntity<?> removePlayersFromSessionByName(@PathVariable String sessionName, @RequestBody List<String> playerNames) {
	        try {
	            SessionTraining updatedSession = scrimsService.removePlayersFromSessionByName(sessionName, playerNames);
	            return ResponseEntity.ok(updatedSession);
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error removing players from session: " + e.getMessage());
	        }
	    }
	 
	 
}
