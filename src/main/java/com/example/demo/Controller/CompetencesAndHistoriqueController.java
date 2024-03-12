package com.example.demo.Controller;

import com.example.demo.Model.CompetencesAndHistorique;
import com.example.demo.Service.CompetencesAndHistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/competencesAndHistorique")
public class CompetencesAndHistoriqueController {

    @Autowired
     CompetencesAndHistoriqueService service;

 // In CompetencesAndHistoriqueController.java
    @PostMapping("/add")
    public CompetencesAndHistorique addCompetenceAndHistorique(@RequestBody Map<String, Object> payload) {
        // Create a new CompetencesAndHistorique instance
        CompetencesAndHistorique competencesAndHistorique = new CompetencesAndHistorique();
        
        // Extract and set values from the payload
        String competence = (String) payload.get("competence");
        String historiquePerformence = (String) payload.get("historiquePerformence");
        Double kdRiot = payload.containsKey("kdRiot") ? ((Number) payload.get("kdRiot")).doubleValue() : null;
        Double winPorsontage = payload.containsKey("winPorsontage") ? ((Number) payload.get("winPorsontage")).doubleValue() : null;
        String playerName = (String) payload.get("playerName"); // Extract player name

        // Set values to competencesAndHistorique
        competencesAndHistorique.setCompetence(competence);
        competencesAndHistorique.setHistoriquePerformence(historiquePerformence);
        competencesAndHistorique.setKdRiot(kdRiot);
        competencesAndHistorique.setWinPorsontage(winPorsontage);

        // Save the competencesAndHistorique with the associated player name
        return service.saveCompetenceAndHistorique(competencesAndHistorique, playerName);
    }


    @GetMapping("/getAll")
    public List<CompetencesAndHistorique> getAllCompetencesAndHistoriques() {
        return service.getAllCompetencesAndHistoriques();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CompetencesAndHistorique> getCompetenceAndHistoriqueById(@PathVariable Long id) {
        CompetencesAndHistorique found = service.getCompetenceAndHistoriqueById(id);
        return found != null ? ResponseEntity.ok(found) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompetencesAndHistorique> updateCompetenceAndHistorique(@PathVariable Long id, @RequestBody CompetencesAndHistorique competencesAndHistoriqueDetails) {
        CompetencesAndHistorique existingCompetencesAndHistorique = service.getCompetenceAndHistoriqueById(id);
        if (existingCompetencesAndHistorique != null) {
            // Update fields from competencesAndHistoriqueDetails to existingCompetencesAndHistorique
            existingCompetencesAndHistorique.setCompetence(competencesAndHistoriqueDetails.getCompetence());
            existingCompetencesAndHistorique.setHistoriquePerformence(competencesAndHistoriqueDetails.getHistoriquePerformence());
            existingCompetencesAndHistorique.setKdRiot(competencesAndHistoriqueDetails.getKdRiot());
            existingCompetencesAndHistorique.setWinPorsontage(competencesAndHistoriqueDetails.getWinPorsontage());
            
            // Save the updated entity
            CompetencesAndHistorique updatedCompetencesAndHistorique = service.updateCompetenceAndHistorique(existingCompetencesAndHistorique);
            return ResponseEntity.ok(updatedCompetencesAndHistorique);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompetenceAndHistorique(@PathVariable Long id) {
        service.deleteCompetenceAndHistoriqueById(id);
        return ResponseEntity.ok().build();
    }
}
