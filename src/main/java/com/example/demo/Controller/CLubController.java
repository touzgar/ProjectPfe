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

import com.example.demo.Service.CLubService;

@RestController
@RequestMapping("/api/club")
@CrossOrigin("*")

public class CLubController {
	@Autowired
	CLubService clubService;
	 @GetMapping("/getAll")
	List<Club> getAllClubs(){
		return clubService.getAllClubs();
	}
	 @GetMapping("/get/{id}")
	public Club getClubById(@PathVariable("id") Long id) {
		return clubService.getClub(id);
	}
	 @PostMapping("/add")
	 public ResponseEntity<?> createClub(@RequestBody Map<String, Object> payload) {
	     try {
	         
	         String description = (String) payload.get("description");
	         String clubName = (String) payload.get("clubName");
	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	          Date dateCreation = dateFormat.parse((String) payload.get("dateCreation"));
	           
	        

	         Club club = new Club();
	         club.setClubName (clubName);
	         club.setDescription(description);
	         club.setDateCreation(dateCreation);
	         // Use the service to handle the logic of adding club and coach by name
	         Club savedClub = clubService.saveClub(club);
	         return ResponseEntity.ok(savedClub);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
	     }
	 }


	 @PutMapping("/update/{id}")
	 public ResponseEntity<?> updateTeam(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
	        try {
	            Club existingClub = clubService.getClub(id);
	            if (existingClub == null) {
	                return ResponseEntity.notFound().build();
	            }

	            // Update player details from payload
	            String clubName = (String) payload.get("clubName");
	            if (clubName != null) existingClub.setClubName(clubName);
	            
	            String description = (String) payload.get("description");
	            if (description != null) existingClub.setDescription(description);
	            
	            
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            if (payload.containsKey("dateCreation")) {
	                Date dateCreation = dateFormat.parse((String) payload.get("dateCreation"));
	                existingClub.setDateCreation(dateCreation);
	            }
	            
	          	            	// Now, save the updated player information
	            Club updatedClub = clubService.UpdateClub(existingClub);

	            return ResponseEntity.ok(updatedClub);
	        } catch (ParseException e) {
	            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
	        }
	    }

	    @DeleteMapping("/delete/{id}")
	public void deleteclub(@PathVariable("id") Long id) {
		clubService.deleteClubById(id);
	}
	 @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public List<Club> searchClubs(@RequestParam("name") String clubName) {
	        return clubService.searchByClubName(clubName);
	    }
}