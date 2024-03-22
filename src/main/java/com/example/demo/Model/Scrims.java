package com.example.demo.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Scrims extends SessionTraining {
    
    private String description;
    private String niveau;
    private String mode;
    private List<String> specialObjectives;

    // List of players who have a Scrims session
    @ManyToMany
    
    private List<Player> scrimsPlayers;
}