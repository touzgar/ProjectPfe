package com.example.demo.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Model.AchivementsTeam;
import com.example.demo.Model.Team;
import com.example.demo.Service.AchivementTeamService;
import com.example.demo.Service.TeamService;
@RestController
@RequestMapping("/api/achivementTeam")
@CrossOrigin("*")

public class AchivementTeamController {
	@Autowired
	private AchivementTeamService achievementTeamService;
	@Autowired
	private TeamService teamService;
	@GetMapping("/getAll")
	List<AchivementsTeam> getAllAchievementTeams(){
		return achievementTeamService.getAllAchivementsTeams();
	}
	
	 @GetMapping("/get/{id}")
	public AchivementsTeam getAchivementsTeamById(@PathVariable("id") Long id) {
		return achievementTeamService.getAchivementsTeam(id);
	}
	 @SuppressWarnings("unchecked")
	@PostMapping("/add")
	 public AchivementsTeam createAchivementsTeam(@RequestBody Map<String, Object> payload) {
	     String teamName = (String) payload.get("teamName"); // This is now getting the team name instead of team ID
	     Optional<Team> teamOptional = teamService.searchByTeamName(teamName).stream().findFirst(); // This assumes searchByTeamName returns a list of teams with similar names, and we take the first

	     if (!teamOptional.isPresent()) {
	         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with name: " + teamName);
	     }
	     Team team = teamOptional.get();

	     AchivementsTeam achivementsTeam = new AchivementsTeam();
	     achivementsTeam.setTournamentName((String) payload.get("tournamentName"));
	     achivementsTeam.setAchievementRank((String) payload.get("achievementRank"));
	     
	     // Parse dateAchived from String to Date
	     String dateString = (String) payload.get("dateAchived");
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Adjust this format to match your input
	     try {
	         Date date = formatter.parse(dateString);
	         achivementsTeam.setDateAchived(date);
	     } catch (ParseException e) {
	         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format: " + dateString);
	     }

	     achivementsTeam.setTrophie((List<String>) payload.get("trophie")); // Ensure this is correctly formatted in JSON
	     achivementsTeam.setTeam(team); // Associate the team with the AchivementsTeam

	     return achievementTeamService.saveAchivementsTeam(achivementsTeam);
	 }
	 @PutMapping("/update/{id}")
	    public ResponseEntity<AchivementsTeam> updateAchivementsTeam(@PathVariable(value = "id") Long achivementId, @RequestBody AchivementsTeam achivementDetails) {
	        try {
	            AchivementsTeam updatedAchivement = achievementTeamService.updateAchivementsTeam(achivementId, achivementDetails);
	            return new ResponseEntity<>(updatedAchivement, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	 
	  @DeleteMapping("/delete/{id}")
	public void deleteAchivementsTeam(@PathVariable("id") Long id) {
		achievementTeamService.deleteAchivementsTeamById(id);
	}

}