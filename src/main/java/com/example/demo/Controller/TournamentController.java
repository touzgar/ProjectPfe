package com.example.demo.Controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Model.Tournament;
import com.example.demo.Service.TournamentService;

@RestController
@RequestMapping("/api/tournament")
@CrossOrigin("*")
public class TournamentController {
@Autowired
TournamentService tournamentService;
	
@GetMapping("/getAll")
List<Tournament> getAllTournaments(){
	return tournamentService.getAllTournaments();
}
 @GetMapping("/get/{id}")
public Tournament getTournamentById(@PathVariable("id") Long id) {
	return tournamentService.getTournament(id);
}
 @PostMapping("/add")
 public ResponseEntity<?> createTournament(@RequestBody Map<String, Object> payload) {
     try {
         String tournamentName = (String) payload.get("tournamentName");
         if (tournamentService.tournamentNameExists(tournamentName)) {
             return ResponseEntity.badRequest().body("Error: Tournament name already exists.");
         }

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
         Tournament tournament = new Tournament();
         tournament.setTournamentName(tournamentName);
         tournament.setFormat((String) payload.get("Format"));
         tournament.setPrizePool(((Number) payload.get("Prizepool")).doubleValue());
         tournament.setDateStart(LocalDateTime.parse((String) payload.get("dateStart"), formatter));
         tournament.setDateEnd(LocalDateTime.parse((String) payload.get("dateEnd"), formatter));
         tournament.setStatus((Boolean) payload.get("status"));
         tournament.setCapacity((Integer) payload.get("capacity"));

         Tournament savedTournament = tournamentService.saveTournament(tournament);
         return ResponseEntity.ok(savedTournament);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error creating tournament: " + e.getMessage());
     }
 }
 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateTeam(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        try {
        	Tournament existingTournament = tournamentService.getTournament(id);
            if (existingTournament == null) {
                return ResponseEntity.notFound().build();
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            
            // Update player details from payload
            String tournamentName = (String) payload.get("tournamentName");
            if (tournamentName != null) existingTournament.setTournamentName(tournamentName);
            
            String Format = (String) payload.get("Format");
            if (Format != null) existingTournament.setFormat(Format);
            
            if (payload.containsKey("dateStart")) {
                String startDateTime = (String) payload.get("dateStart");
                LocalDateTime dateStart = LocalDateTime.parse(startDateTime, formatter);
                existingTournament.setDateStart(dateStart);
            }
            
            if (payload.containsKey("dateEnd")) {
                String endDateTime = (String) payload.get("dateEnd");
                LocalDateTime dateEnd = LocalDateTime.parse(endDateTime, formatter);
                existingTournament.setDateEnd(dateEnd);
            }
            if (payload.containsKey("salary")) {
                Double PrizePool = ((Number) payload.get("PrizePool")).doubleValue();
                existingTournament.setPrizePool(PrizePool);
            }
          if(payload.containsKey("status")) {
        	  Boolean status = (Boolean) payload.get("status");
        	  existingTournament.setStatus(status);
          }
          if (payload.containsKey("capacity")) { // Check for capacity in the payload
              existingTournament.setCapacity((Integer) payload.get("capacity")); // Update capacity
          }
          	            	// Now, save the updated player information
            Tournament updatedTournament = tournamentService.UpdateTournament(existingTournament);

            return ResponseEntity.ok(updatedTournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
        }
    }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<?> deleteTournament(@PathVariable("id") Long id) {
     try {
         tournamentService.deleteTournamentById(id);
         return ResponseEntity.ok().build();
     } catch (RuntimeException e) {
         return ResponseEntity.badRequest().body(e.getMessage());
     }
 }

    @PostMapping("/registerTeams")
    public ResponseEntity<?> registerTeamsInTournament(@RequestBody Map<String, Object> payload) {
        try {
            String tournamentName = (String) payload.get("tournamentName");
            List<String> teamNames = (List<String>) payload.get("teamNames");
            Tournament updatedTournament = tournamentService.registerTeamsInTournament(tournamentName, teamNames);
            return ResponseEntity.ok(updatedTournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering teams: " + e.getMessage());
        }
    }
    @PostMapping("/removeTeams")
    public ResponseEntity<?> removeTeamsFromTournament(@RequestBody Map<String, Object> payload) {
        try {
            String tournamentName = (String) payload.get("tournamentName");
            List<String> teamNames = (List<String>) payload.get("teamNames");
            Tournament updatedTournament = tournamentService.removeTeamsFromTournament(tournamentName, teamNames);
            return ResponseEntity.ok(updatedTournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing teams: " + e.getMessage());
        }
}
    @PostMapping("/addMatch")
    public ResponseEntity<?> addMatchToTournament(@RequestBody Map<String, Object> payload) {
        try {
            String tournamentName = (String) payload.get("tournamentName");
            String matchDescription = (String) payload.get("matchDescription"); // "Team1 vs Team2"
            String matchDateTimeStr = (String) payload.get("matchDateTime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime matchDateTime = LocalDateTime.parse(matchDateTimeStr, formatter);

            Tournament updatedTournament = tournamentService.addMatchAndEnsureTeamRegistration(tournamentName, matchDescription, matchDateTime);
            return ResponseEntity.ok(updatedTournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding match: " + e.getMessage());
        }
        
    }
    @DeleteMapping("/deleteMatchFromTournament/{idDefi}")
    public ResponseEntity<?> deleteMatchFromTournament(@PathVariable Long idDefi) {
        try {
            tournamentService.deleteMatchFromTournament(idDefi);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/historical")
    public ResponseEntity<List<Tournament>> getHistoricalTournaments() {
        List<Tournament> historicalTournaments = tournamentService.getHistoricalTournaments();
        return ResponseEntity.ok(historicalTournaments);
    }

}
