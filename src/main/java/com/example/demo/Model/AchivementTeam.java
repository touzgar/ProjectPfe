package com.example.demo.Model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchivementTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achivementId;
    private List<String> trophies; // Renamed from 'Trophie'
    private Date dateAchived; // Renamed from 'dateAchived'
    private String achievementRank; // Renamed from 'Rank'
    
    @ManyToOne // Many achievements belong to one team
    private Team team;
}

