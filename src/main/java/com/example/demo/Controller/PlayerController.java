package com.example.demo.Controller;


import com.example.demo.Model.Player;
import com.example.demo.Model.Team;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/player")
@CrossOrigin("*")
public class PlayerController {

    @Autowired
  private  PlayerService playerService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/getAll")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/get/{id}")
    public Player getPlayerById(@PathVariable("id") Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createPlayer(@RequestBody Map<String, Object> payload) {
        try {
            // Extract and cast all necessary fields from the payload
            String leagalefullname = (String) payload.get("leagalefullname");
            String inGameName = (String) payload.get("inGameName");
            String teamName = payload.containsKey("teamName") ? (String) payload.get("teamName") : null;
            String countryOfResidence = (String) payload.get("countryOfResidence");
            String discordId = (String) payload.get("discordId");
            String jerseySize = (String) payload.get("jerseySize");
            String mailAdress = (String) payload.get("mailAdress");
            Double salary = ((Number) payload.get("salary")).doubleValue();
            String socialMediaLinks = (String) payload.get("socialMediaLinks");
            String whatsappPhone = (String) payload.get("whatsappPhone");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date contratEnd = dateFormat.parse((String) payload.get("contratEnd"));
            Date contratStart = dateFormat.parse((String) payload.get("contratStart"));
            Date dateOfBirth = dateFormat.parse((String) payload.get("dateOfBirth"));

            // Create a new Player instance and set extracted fields
            Player player = new Player();
            player.setLeagalefullname(leagalefullname);
            player.setInGameName(inGameName);
            player.setCountryOfResidence(countryOfResidence);
            player.setDiscordId(discordId);
            player.setJerseySize(jerseySize);
            player.setMailAdress(mailAdress);
            player.setSalary(salary);
            player.setSocialMediaLinks(socialMediaLinks);
            player.setWhatsappPhone(whatsappPhone);
            player.setContratEnd(contratEnd);
            player.setContratStart(contratStart);
            player.setDateOfBirth(dateOfBirth);

            // Optional team assignment
            if (teamName != null && !teamName.isEmpty()) {
                Team team = teamRepository.findByTeamName(teamName)
                    .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));
                player.setTeam(team);
            }

            // Save the player, with or without a team
            Player savedPlayer = playerRepository.save(player);
            return ResponseEntity.ok(savedPlayer);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while creating the player: " + e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        try {
            Player existingPlayer = playerService.getPlayer(id);
            if (existingPlayer == null) {
                return ResponseEntity.notFound().build();
            }

            // Update player details from payload
            String leagalefullname = (String) payload.get("leagalefullname");
            if (leagalefullname != null) existingPlayer.setLeagalefullname(leagalefullname);
            
            String inGameName = (String) payload.get("inGameName");
            if (inGameName != null) existingPlayer.setInGameName(inGameName);
            
            String countryOfResidence = (String) payload.get("countryOfResidence");
            if (countryOfResidence != null) existingPlayer.setCountryOfResidence(countryOfResidence);
            
            String discordId = (String) payload.get("discordId");
            if (discordId != null) existingPlayer.setDiscordId(discordId);
            
            String jerseySize = (String) payload.get("jerseySize");
            if (jerseySize != null) existingPlayer.setJerseySize(jerseySize);
            
            String mailAdress = (String) payload.get("mailAdress");
            if (mailAdress != null) existingPlayer.setMailAdress(mailAdress);
            
            if (payload.containsKey("salary")) {
                Double salary = ((Number) payload.get("salary")).doubleValue();
                existingPlayer.setSalary(salary);
            }
            
            String socialMediaLinks = (String) payload.get("socialMediaLinks");
            if (socialMediaLinks != null) existingPlayer.setSocialMediaLinks(socialMediaLinks);
            
            String whatsappPhone = (String) payload.get("whatsappPhone");
            if (whatsappPhone != null) existingPlayer.setWhatsappPhone(whatsappPhone);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (payload.containsKey("contratEnd")) {
                Date contratEnd = dateFormat.parse((String) payload.get("contratEnd"));
                existingPlayer.setContratEnd(contratEnd);
            }
            
            if (payload.containsKey("contratStart")) {
                Date contratStart = dateFormat.parse((String) payload.get("contratStart"));
                existingPlayer.setContratStart(contratStart);
            }
            
            if (payload.containsKey("dateOfBirth")) {
                Date dateOfBirth = dateFormat.parse((String) payload.get("dateOfBirth"));
                existingPlayer.setDateOfBirth(dateOfBirth);
            }
            
            // Update the team association, if a new team name is provided
            String newTeamName = (String) payload.get("teamName");
            if (newTeamName != null && !newTeamName.trim().isEmpty()) {
                Team newTeam = teamRepository.findByTeamName(newTeamName)
                              .orElseThrow(() -> new RuntimeException("Team with name '" + newTeamName + "' not found"));
                existingPlayer.setTeam(newTeam);
            }

            // Now, save the updated player information
            Player updatedPlayer = playerService.updatePlayer(existingPlayer);

            return ResponseEntity.ok(updatedPlayer);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while updating the player: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deletePlayer(@PathVariable("id") Long id) {
        playerService.deletePlayerById(id);
    }
    @GetMapping("/getbyname/{leagalefullname}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable("leagalefullname") String leagalefullname) {
        return playerService.findPlayerByName(leagalefullname)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
     
}