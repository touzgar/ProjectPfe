package com.example.demo.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AchievementPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAchievementPlayer;
    private String playerName;
    @ElementCollection // This annotation is used for storing a collection of basic types
    private List<String> trophie; 
    private Date dateAchievement;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    // Getters and Setters

    
   
    
    
    
		
	
	


   
}