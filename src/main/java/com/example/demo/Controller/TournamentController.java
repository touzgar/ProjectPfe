package com.example.demo.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
         String Format = (String) payload.get("Format");
         Double PrizePool=((Number) payload.get("Prizepool")).doubleValue();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         Date dateStart = dateFormat.parse((String) payload.get("dateStart"));
         Date dateEnd = dateFormat.parse((String) payload.get("dateEnd"));
         Boolean status = (Boolean) payload.get("status");

        

         Tournament tournament = new Tournament();
         tournament.setTournamentName(tournamentName);
         tournament.setFormat(Format);
         tournament.setPrizePool(PrizePool);
         tournament.setDateEnd(dateEnd);
         tournament.setDateStart(dateStart);
         tournament.setStatus(status);
         
         // Use the service to handle the logic of adding club and coach by name
         Tournament savedTournament =  tournamentService.saveTournament(tournament);
         return ResponseEntity.ok(savedTournament);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
     }
 }


 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateTeam(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        try {
        	Tournament existingTournament = tournamentService.getTournament(id);
            if (existingTournament == null) {
                return ResponseEntity.notFound().build();
            }

            // Update player details from payload
            String tournamentName = (String) payload.get("tournamentName");
            if (tournamentName != null) existingTournament.setTournamentName(tournamentName);
            
            String Format = (String) payload.get("Format");
            if (Format != null) existingTournament.setFormat(Format);
            
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (payload.containsKey("dateEnd")) {
                Date dateEnd = dateFormat.parse((String) payload.get("dateEnd"));
                existingTournament.setDateEnd(dateEnd);
            }
            if (payload.containsKey("dateStart")) {
                Date dateStart = dateFormat.parse((String) payload.get("dateStart"));
                existingTournament.setDateStart(dateStart);
            }
            if (payload.containsKey("salary")) {
                Double PrizePool = ((Number) payload.get("PrizePool")).doubleValue();
                existingTournament.setPrizePool(PrizePool);
            }
          if(payload.containsKey("status")) {
        	  Boolean status = (Boolean) payload.get("status");
        	  existingTournament.setStatus(status);
          }
          	            	// Now, save the updated player information
            Tournament updatedTournament = tournamentService.UpdateTournament(existingTournament);

            return ResponseEntity.ok(updatedTournament);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
public void deleteclub(@PathVariable("id") Long id) {
	tournamentService.deleteTournamentById(id);
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
}
