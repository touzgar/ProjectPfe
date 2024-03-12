package com.example.demo.Controller;

import com.example.demo.Model.AchievementPlayer;
import com.example.demo.Service.AchievementPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<AchievementPlayer> updateAchievementPlayer(@PathVariable Long id, @RequestBody AchievementPlayer achievementPlayerDetails) {
        AchievementPlayer existingAchievementPlayer = service.getAchievementPlayerById(id);
        if (existingAchievementPlayer != null) {
            existingAchievementPlayer.setPlayerName(achievementPlayerDetails.getPlayerName());
            existingAchievementPlayer.setTrophie(achievementPlayerDetails.getTrophie());
            existingAchievementPlayer.setDateAchievement(achievementPlayerDetails.getDateAchievement());
            existingAchievementPlayer.setStatus(achievementPlayerDetails.getStatus());
            AchievementPlayer updatedAchievementPlayer = service.updateAchievementPlayer(existingAchievementPlayer);
            return ResponseEntity.ok(updatedAchievementPlayer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAchievementPlayer(@PathVariable Long id) {
        service.deleteAchievementPlayerById(id);
        return ResponseEntity.ok().build();
    }
}