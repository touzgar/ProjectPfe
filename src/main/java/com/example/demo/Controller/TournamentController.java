package com.example.demo.Controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Defi;
import com.example.demo.Model.Player;
import com.example.demo.Model.Team;
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
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
         Date dateStart = dateFormat.parse((String) payload.get("dateStart"));
         Date dateEnd = dateFormat.parse((String) payload.get("dateEnd"));

         // Other fields remain the same
         String tournamentName = (String) payload.get("tournamentName");
         String format = (String) payload.get("format");
         Double prizePool = ((Number) payload.get("prizePool")).doubleValue();
         Boolean status = (Boolean) payload.get("status");
         Integer capacity = (Integer) payload.get("capacity");

         // Use the parsed Date objects
         Tournament tournament = new Tournament();
         tournament.setTournamentName(tournamentName);
         tournament.setFormat(format);
         tournament.setPrizePool(prizePool);
         tournament.setDateStart(dateStart); // Use the parsed Date
         tournament.setDateEnd(dateEnd); // Use the parsed Date
         tournament.setStatus(status);
         tournament.setCapacity(capacity);

         Tournament savedTournament = tournamentService.saveTournament(tournament);
         return ResponseEntity.ok(savedTournament);
     } catch (ParseException e) {
         return ResponseEntity.badRequest().body("Error parsing date: " + e.getMessage());
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            // Update player details from payload
            String tournamentName = (String) payload.get("tournamentName");
            if (tournamentName != null) existingTournament.setTournamentName(tournamentName);
            
            String Format = (String) payload.get("Format");
            if (Format != null) existingTournament.setFormat(Format);
            if (payload.containsKey("dateStart")) {
                Date dateStart = dateFormat.parse((String) payload.get("dateStart"));
                existingTournament.setDateStart(dateStart);
            }
            if (payload.containsKey("dateEnd")) {
                Date dateEnd = dateFormat.parse((String) payload.get("dateEnd"));
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
 public ResponseEntity<?> registerTeamsInTournament(
         @RequestParam("tournamentName") String tournamentName,
         @RequestParam("teamNames") List<String> teamNames) {
     try {
         Tournament updatedTournament = tournamentService.registerTeamsInTournament(tournamentName, teamNames);
         return ResponseEntity.ok(updatedTournament);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error registering teams: " + e.getMessage());
     }
 }
    @DeleteMapping("/removeTeams")
    public ResponseEntity<?> removeTeamsFromTournament(@RequestParam("tournamentName") String tournamentName,
            											@RequestParam("teamNames") List<String> teamNames) {
        try {
            Tournament updatedTournament=tournamentService.removeTeamsFromTournament(tournamentName, teamNames);
            return ResponseEntity.ok(updatedTournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing teams: " + e.getMessage());
        }
}
 /*   @DeleteMapping("/removePlayersFromTeamByNames")
	 public ResponseEntity<?> removePlayersFromTeamByNames(@RequestParam("teamName") String teamName, 
	                                                       @RequestParam("playerNames") List<String> playerNames) {
	     try {
	         Team updatedTeam = teamService.removePlayersFromTeamByNames(teamName, playerNames);
	         return ResponseEntity.ok(updatedTeam);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("An error occurred while removing players from team: " + e.getMessage());
	     }
	 }
*/
    
    
    
    
    
    @PostMapping("/addMatch")
    public ResponseEntity<?> addMatchToTournament(@RequestBody Map<String, Object> payload) {
        try {
            String tournamentName = (String) payload.get("tournamentName");
            String matchDescription = (String) payload.get("matchDescription");
            String matchDateTimeStr = (String) payload.get("matchDateTime");

            // Use SimpleDateFormat to parse the string into Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date matchDateTime;
            try {
                matchDateTime = dateFormat.parse(matchDateTimeStr);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error parsing match date/time: " + e.getMessage());
            }

            // Assuming the service layer now accepts Date:
            Tournament updatedTournament = tournamentService.addMatchAndEnsureTeamRegistration(
                    tournamentName, matchDescription, matchDateTime);

            return ResponseEntity.ok(updatedTournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding match: " + e.getMessage());
        }
    }    @DeleteMapping("/deleteMatchFromTournament/{idDefi}")
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
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Tournament> searchTournament(@RequestParam("name") String tournamentName) {
        return tournamentService.searchByTournamentName(tournamentName);
    }


}
