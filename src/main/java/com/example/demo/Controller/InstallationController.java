package com.example.demo.Controller;

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

import com.example.demo.Model.Installation;
import com.example.demo.Model.Ressources;
import com.example.demo.Model.Team;
import com.example.demo.Service.InstallationService;
import com.example.demo.Service.RessourceService;
import com.example.demo.Service.TeamService;

@RestController
@RequestMapping("/api/installation")
@CrossOrigin("*")
public class InstallationController {
@Autowired
InstallationService installationService;
@Autowired
TeamService teamService;
@GetMapping("/getAll")
List<Installation> getAllInstallations(){
	return installationService.getAllInstallation();
}
 @GetMapping("/get/{id}")
public Installation getInstallationById(@PathVariable("id") Long id) {
	return installationService.getInstallation(id);
}

 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateInstallation(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
     try {
         Installation existingInstallation = installationService.getInstallation(id);
         if (existingInstallation == null) {
             return ResponseEntity.notFound().build();
         }

         // Update installation details from payload
         if (payload.containsKey("installationName")) {
             existingInstallation.setInstallationName((String) payload.get("installationName"));
         }

         if (payload.containsKey("type")) {
             existingInstallation.setType((String) payload.get("type"));
         }

         if (payload.containsKey("disponibilite")) {
             existingInstallation.setDisponibilite((Boolean) payload.get("disponibilite"));
         }

         if (payload.containsKey("capacite")) {
             existingInstallation.setCapacite((Integer) payload.get("capacite"));
         }

         // Save the updated installation information
         Installation updatedInstallation = installationService.saveInstallation(existingInstallation);

         return ResponseEntity.ok(updatedInstallation);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("An error occurred while updating the installation: " + e.getMessage());
     }
 }

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<?> deleteInstallation(@PathVariable("id") Long id) {
     try {
         installationService.deleteInstallationById(id);
         return ResponseEntity.ok().build();
     } catch (RuntimeException e) {
         return ResponseEntity.notFound().build();
     } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
     }
 }

 @PostMapping("/add")
 public ResponseEntity<?> createInstallation(@RequestBody Map<String, Object> payload) {
     try {
         // Extract fields from the payload
         String installationName = (String) payload.get("installationName");
         String type = (String) payload.get("type");
         Boolean disponibilite = (Boolean) payload.get("disponibilite");
         Integer capacite = (Integer) payload.get("capacite");
         String teamName = (String) payload.get("teamName");

         // Find the corresponding team
         Team team = teamService.getTeamByName(teamName);
         if (team == null) {
             return ResponseEntity.badRequest().body("Team with name " + teamName + " not found");
         }

         // Create and save the new Installation
         Installation installation = new Installation();
         installation.setInstallationName(installationName);
         installation.setType(type);
         installation.setDisponibilite(disponibilite);
         installation.setCapacite(capacite);
         installation.setTeam(team); // Set the team

         Installation savedInstallation = installationService.saveInstallation(installation);
         return ResponseEntity.ok(savedInstallation);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error creating installation: " + e.getMessage());
     }
 }    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Installation> searchInstallations(@RequestParam("name") String installationName) {
        return installationService.searchByInstallationName(installationName);
    }
}
