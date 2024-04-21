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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Club;
import com.example.demo.Model.Player;
import com.example.demo.Model.Team;
import com.example.demo.Repository.ClubRepository;
import com.example.demo.Service.TeamService;

@RestController
@RequestMapping("/api/team")
@CrossOrigin("*")

public class TeamController {
	@Autowired
	TeamService teamService;
	@Autowired
	ClubRepository clubRepository;
	 @GetMapping("/getAll")
	List<Team> getAllTeams(){
		return teamService.getAllTeams();
	}
	 @GetMapping("/get/{id}")
	public Team getTeamById(@PathVariable("id") Long id) {
		return teamService.getTeam(id);
	}
	 @PostMapping("/add")
	 public ResponseEntity<?> createTeam(@RequestBody Map<String, Object> payload) {
	     try {
	         String teamName = (String) payload.get("teamName");
	         String description = (String) payload.get("description");
	         String clubName = (String) payload.get("clubName");
	        
	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	          Date dateCreation = dateFormat.parse((String) payload.get("dateCreation"));
	         

	         Team team = new Team();
	         team.setTeamName(teamName);
	         team.setDescription(description);
	         team.setDateCreation(dateCreation);
	         
	         // Use the service to handle the logic of adding club and coach by name
	         Team savedTeam = teamService.saveTeamWithClubName(team, clubName);
	         return ResponseEntity.ok(savedTeam);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
	     }
	 }
	 @PutMapping("/update/{id}")
	 public ResponseEntity<?> updateTeam(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
	        try {
	            Team existingTeam = teamService.getTeam(id);
	            if (existingTeam == null) {
	                return ResponseEntity.notFound().build();
	            }

	            // Update player details from payload
	            String teamName = (String) payload.get("teamName");
	            if (teamName != null) existingTeam.setTeamName(teamName);
	            
	            String description = (String) payload.get("description");
	            if (description != null) existingTeam.setDescription(description);
	            
	            
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            if (payload.containsKey("dateCreation")) {
	                Date dateCreation = dateFormat.parse((String) payload.get("dateCreation"));
	                existingTeam.setDateCreation(dateCreation);
	            }
	            
	            // Update the team association, if a new team name is provided
	            String newClubName = (String) payload.get("clubName");
	            if (newClubName != null && !newClubName.trim().isEmpty()) {
	                Club newClub = clubRepository.findByClubName(newClubName)
	                              .orElseThrow(() -> new RuntimeException("Team with name '" + newClubName + "' not found"));
	                existingTeam.setClub(newClub);
	            }

	            	// Now, save the updated player information
	            Team updatedTeam = teamService.UpdateTeam(existingTeam);

	            return ResponseEntity.ok(updatedTeam);
	        } catch (ParseException e) {
	            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
	        }
	    }

	 @DeleteMapping("/delete/{id}")
	public void deleteclub(@PathVariable("id") Long id) {
		teamService.deleteTeamById(id);
	}
	 @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public List<Team> searchTeams(@RequestParam("name") String teamName) {
	        return teamService.searchByTeamName(teamName);
	    }
	 @PostMapping("/addCoachToTeam")
	    public ResponseEntity<?> addCoachToTeam(@RequestParam("teamName") String teamName, 
	                                            @RequestParam("coachName") String coachName) {
	        try {
	            Team updatedTeam = teamService.addCoachToTeam(teamName, coachName);
	            return ResponseEntity.ok(updatedTeam);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while adding coach to team: " + e.getMessage());
	        }
	    }
	 @DeleteMapping("/removeCoachFromTeam")
	 public ResponseEntity<?> removeCoachFromTeam(@RequestParam("teamName") String teamName,
	                                              @RequestParam("coachName") String coachName) {
	     try {
	         teamService.removeCoachFromTeam(teamName, coachName);
	         return ResponseEntity.ok("Coach removed from team successfully");
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("An error occurred while removing coach from team: " + e.getMessage());
	     }
	 }
	// In TeamController.java
	 @PostMapping("/addPlayersToTeamByNames")
	 public ResponseEntity<?> addPlayersToTeamByNames(@RequestParam("teamName") String teamName, 
	                                                   @RequestParam("playerNames") List<String> playerNames) {
	     try {
	         Team updatedTeam = teamService.addPlayersToTeamByNames(teamName, playerNames);
	         return ResponseEntity.ok(updatedTeam);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("An error occurred while adding players to team: " + e.getMessage());
	     }
	 }
	// Spring Boot Controller
	 @DeleteMapping("/removePlayersFromTeamByNames")
	 public ResponseEntity<?> removePlayersFromTeamByNames(@RequestParam("teamName") String teamName, 
	                                                       @RequestParam("playerNames") List<String> playerNames) {
	     try {
	         Team updatedTeam = teamService.removePlayersFromTeamByNames(teamName, playerNames);
	         return ResponseEntity.ok(updatedTeam);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("An error occurred while removing players from team: " + e.getMessage());
	     }
	 }
	 @GetMapping("/getPlayersByTeamNames")
	 public ResponseEntity<?> getPlayersByTeamNames(@RequestParam List<String> teamNames) {
	     try {
	         List<Player> players = teamService.getPlayersByTeamNames(teamNames);
	         if (players.isEmpty()) {
	             return ResponseEntity.notFound().build();
	         }
	         return ResponseEntity.ok(players);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Failed to fetch players: " + e.getMessage());
	     }
	 }




	 
}
