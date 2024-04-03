package com.example.demo.Controller;

import com.example.demo.Model.AchievementPlayer;
import com.example.demo.Service.AchievementPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/achievementPlayer")
public class AchievementPlayerController {

    @Autowired
    private AchievementPlayerService service;

    @PostMapping("/add")
    public ResponseEntity<AchievementPlayer> addAchievementPlayer(@RequestBody AchievementPlayer achievementPlayer) {
        try {
            AchievementPlayer newAchievementPlayer = service.createAchievementPlayer(achievementPlayer);
            return ResponseEntity.ok(newAchievementPlayer);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null); // Consider customizing the error handling
        }
    }
    
  
    @GetMapping("/getAll")
    public List<AchievementPlayer> getAllAchievementPlayers() {
        return service.getAllAchievementPlayers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AchievementPlayer> getAchievementPlayerById(@PathVariable Long id) {
        AchievementPlayer found = service.getAchievementPlayerById(id);
        return found != null ? ResponseEntity.ok(found) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAchievementPlayer(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        try {
            AchievementPlayer existingAchievementPlayer = service.getAchievementPlayerById(id);
            if (existingAchievementPlayer == null) {
                return ResponseEntity.notFound().build();
            }

            // Update AchievementPlayer details from payload
            String playerName = (String) payload.get("playerName");
            if (playerName != null) existingAchievementPlayer.setPlayerName(playerName);
            
            List<String> trophie = (List<String>) payload.get("trophie");
            if (trophie != null) existingAchievementPlayer.setTrophie(trophie);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (payload.containsKey("dateAchievement")) {
                Date dateAchievement = dateFormat.parse((String) payload.get("dateAchievement"));
                existingAchievementPlayer.setDateAchievement(dateAchievement);
            }
            
            // Now, save the updated AchievementPlayer information
            AchievementPlayer updatedAchievementPlayer = service.updateAchievementPlayer(existingAchievementPlayer);

            return ResponseEntity.ok(updatedAchievementPlayer);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while updating the AchievementPlayer: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAchievementPlayer(@PathVariable Long id) {
        service.deleteAchievementPlayerById(id);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<AchievementPlayer> searchAchivementsPlayers(@RequestParam("name") String playerName) {
        return service.searchByPlayerName(playerName);
    }

    
    
    
}