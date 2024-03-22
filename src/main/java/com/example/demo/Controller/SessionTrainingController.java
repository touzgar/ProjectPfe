package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Coach;
import com.example.demo.Model.Player;
import com.example.demo.Model.SessionTraining;
import com.example.demo.Repository.CoachRepository;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Service.PlayerService;
import com.example.demo.Service.SessionTrainingService;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/session")
@CrossOrigin("*")
public class SessionTrainingController {

	@Autowired
	SessionTrainingService sessionTrainingService;
	@Autowired
	PlayerService playerService;
	@Autowired
	CoachRepository coachRepository;
	@Autowired
	PlayerRepository playerRepository;
	 @GetMapping("/getAll")
		List<SessionTraining> getAllSessionTrainings(){
				return sessionTrainingService.getAllSessionTrainings();
		}
	 @GetMapping("/get/{id}")
		public SessionTraining getSessionTrainingById(@PathVariable("id") Long id) {
				return sessionTrainingService.getSessionTraining(id);
		}
	 @DeleteMapping("/delete/{id}")
		public void deleteSessionTraining(@PathVariable("id") Long id) {
			    	sessionTrainingService.deleteSessionTrainingById(id);
		}
	 @RequestMapping(value = "/search", method = RequestMethod.GET)
	 	public List<SessionTraining> searchSessionTrainings(@RequestParam("name") String sessionName) {
			        return sessionTrainingService.searchBySessionName(sessionName);
		}
	 

	 @PutMapping("/update/{id}")
	    public ResponseEntity<?> updateSessionTraining(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
	        try {
	            String coachName = (String) payload.get("coachName");
	            List<String> playerNames = (List<String>) payload.get("playerNames");
	            SessionTraining sessionTrainingDetails = mapToSessionTraining(payload);
	            
	            SessionTraining updatedSessionTraining = sessionTrainingService.updateSessionTraining(
	                id, coachName, playerNames, sessionTrainingDetails
	            );

	            return ResponseEntity.ok(updatedSessionTraining);
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error updating session training: " + e.getMessage());
	        }
	    }

	    private SessionTraining mapToSessionTraining(Map<String, Object> payload) {
	        SessionTraining sessionTraining = new SessionTraining();
	        sessionTraining.setSessionName((String) payload.get("sessionName"));
	        LocalDateTime dateStart = LocalDateTime.parse((String) payload.get("dateStart"));
	        LocalDateTime dateEnd = LocalDateTime.parse((String) payload.get("dateEnd"));
	        sessionTraining.setDateStart(dateStart);
	        sessionTraining.setDateEnd(dateEnd);
	        sessionTraining.setObjectifs((List<String>) payload.get("objectifs"));
	        sessionTraining.setFeedbacksEntraineurs((String) payload.get("feedbacksEntraineurs"));
	        return sessionTraining;
	    }	 
	 
	 
	 @PostMapping("/add")
	 public ResponseEntity<?> createSessionTraining(@RequestBody Map<String, Object> payload) {
	     try {
	         String coachName = (String) payload.get("coachName");
	         List<String> playerNames = (List<String>) payload.get("playerNames");
	         String sessionName = (String) payload.get("sessionName");
	         LocalDateTime dateStart = LocalDateTime.parse((String) payload.get("dateStart"));
	         LocalDateTime dateEnd = LocalDateTime.parse((String) payload.get("dateEnd"));
	         List<String> objectifs = (List<String>) payload.get("objectifs");
	         String feedbacksEntraineurs = (String) payload.get("feedbacksEntraineurs");
	         
	         Coach coach = coachRepository.findByNameCoachIgnoreCase(coachName)
	             .orElseThrow(() -> new EntityNotFoundException("Coach not found"));
	         
	         List<Player> players = playerRepository.findByLeagalefullnameInIgnoreCase(playerNames);
	         if (players.isEmpty()) {
	             throw new EntityNotFoundException("Players not found");
	         }
	         
	         SessionTraining sessionTraining = new SessionTraining();
	         sessionTraining.setSessionName(sessionName);
	         sessionTraining.setDateStart(dateStart);
	         sessionTraining.setDateEnd(dateEnd);
	         sessionTraining.setObjectifs(objectifs);
	         sessionTraining.setFeedbacksEntraineurs(feedbacksEntraineurs);
	         sessionTraining.setCoach(coach);
	         sessionTraining.setPresencePlayer(players);
	         
	         SessionTraining savedSessionTraining = sessionTrainingService.createSessionTraining(coachName, playerNames, sessionTraining);
	         return ResponseEntity.ok(savedSessionTraining);
	         
	     } catch (DateTimeParseException e) {
	         return ResponseEntity.badRequest().body("Error parsing date fields: " + e.getMessage());
	     } catch (EntityNotFoundException e) {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating session training: " + e.getMessage());
	     }
	 }
	  @GetMapping("/getPlayersBySession/{sessionName}")
	    public ResponseEntity<?> getPlayersBySessionName(@PathVariable String sessionName) {
	        try {
	            List<Player> players = sessionTrainingService.getPlayersBySessionName(sessionName);
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
	            SessionTraining updatedSession = sessionTrainingService.removePlayersFromSessionByName(sessionName, playerNames);
	            return ResponseEntity.ok(updatedSession);
	        } catch (EntityNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error removing players from session: " + e.getMessage());
	        }
	    }
	 
}
