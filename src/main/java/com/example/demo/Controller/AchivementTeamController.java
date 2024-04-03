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

import com.example.demo.Model.AchivementTeam;

import com.example.demo.Service.AchivementTeamService;

@RestController
@RequestMapping("/api/achivementTeam")
@CrossOrigin("*")
public class AchivementTeamController {
	@Autowired
	AchivementTeamService achivementTeamService;
	
	@GetMapping("/getAll")
	List<AchivementTeam> getAllAchivementTeams(){
		return achivementTeamService.getAllAchivementTeams();
	}
	 @GetMapping("/get/{id}")
	public AchivementTeam getAchivementTeamById(@PathVariable("id") Long id) {
		return achivementTeamService.getAchivementTeam(id);
	}
	 @PostMapping("/add")
	 public ResponseEntity<?> createAchivementTeam(@RequestBody Map<String, Object> payload) {
	     try {
	         
	         String Rank = (String) payload.get("Rank");
	       //  String teamName = (String) payload.get("teamName");
	         List<String>Trophie = (List<String>)payload.get("Trophie");

	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	          Date dateAchived = dateFormat.parse((String) payload.get("dateAchived"));
	           
	        

	          AchivementTeam achivementTeam = new AchivementTeam();
	          achivementTeam.setDateAchived(dateAchived);
	          achivementTeam.setAchievementRank(Rank);
	          achivementTeam.setTrophies(Trophie);
	         // Use the service to handle the logic of adding club and coach by name
	         AchivementTeam savedAchivementTeam = achivementTeamService.saveAchivementTeam(achivementTeam);
	         return ResponseEntity.ok(savedAchivementTeam);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
	     }
	 }
	 @PutMapping("/update/{id}")
	 public ResponseEntity<?> updateAchievementTeam(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
	     try {
	         AchivementTeam existingAchivementTeam = achivementTeamService.getAchivementTeam(id);
	         if (existingAchivementTeam == null) {
	             return ResponseEntity.notFound().build();
	         }

	         // Update achievement team details from payload
	         if (payload.containsKey("Rank")) {
	             existingAchivementTeam.setAchievementRank((String) payload.get("Rank"));
	         }

	         if (payload.containsKey("Trophie")) {
	             existingAchivementTeam.setTrophies((List<String>) payload.get("Trophie"));
	         }

	         if (payload.containsKey("dateAchived")) {
	             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	             Date dateAchieved = dateFormat.parse((String) payload.get("dateAchived"));
	             existingAchivementTeam.setDateAchived(dateAchieved);
	         }

	         // Now, save the updated achievement team information
	         AchivementTeam updatedAchievementTeam = achivementTeamService.UpdateAchivementTeam(existingAchivementTeam);

	         return ResponseEntity.ok(updatedAchievementTeam);
	     } catch (ParseException e) {
	         return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
	     }
	 }
	 @DeleteMapping("/delete/{id}")
		public void deleteAchivementTeam(@PathVariable("id") Long id) {
			achivementTeamService.deleteAchivementTeamById(id);
		}
	 @PostMapping("/addWithTeam")
	    public ResponseEntity<?> createAchievementWithTeam(@RequestBody Map<String, Object> payload) {
	        try {
	            // Assume extraction and creation of AchivementTeam object from payload
	            AchivementTeam achivementTeam = new AchivementTeam();
	            // Populate achivementTeam properties from payload...

	            String teamName = (String) payload.get("teamName");
	            AchivementTeam savedAchievementTeam = achivementTeamService.saveAchievementWithTeam(achivementTeam, teamName);
	            return ResponseEntity.ok(savedAchievementTeam);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error creating achievement with team: " + e.getMessage());
	        }


	 }
}
