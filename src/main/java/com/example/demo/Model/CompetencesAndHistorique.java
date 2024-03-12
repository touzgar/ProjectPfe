package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CompetencesAndHistorique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompetence;
    private String competence;
    private String historiquePerformence;
    private Double kdRiot;
    private Double winPorsontage;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    // Getter and Setter
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    
    
	public Long getIdCompetence() {
		return idCompetence;
	}
	public void setIdCompetence(Long idCompetence) {
		this.idCompetence = idCompetence;
	}
	public String getCompetence() {
		return competence;
	}
	public void setCompetence(String competence) {
		this.competence = competence;
	}
	public String getHistoriquePerformence() {
		return historiquePerformence;
	}
	public void setHistoriquePerformence(String historiquePerformence) {
		this.historiquePerformence = historiquePerformence;
	}
	public Double getKdRiot() {
		return kdRiot;
	}
	public void setKdRiot(Double kdRiot) {
		this.kdRiot = kdRiot;
	}
	public Double getWinPorsontage() {
		return winPorsontage;
	}
	public void setWinPorsontage(Double winPorsontage) {
		this.winPorsontage = winPorsontage;
	}
	
	public CompetencesAndHistorique(Long idCompetence, String competence, String historiquePerformence, Double kdRiot,
			Double winPorsontage, String playerName) {
		super();
		this.idCompetence = idCompetence;
		this.competence = competence;
		this.historiquePerformence = historiquePerformence;
		this.kdRiot = kdRiot;
		this.winPorsontage = winPorsontage;
		}
	public CompetencesAndHistorique() {
		super();
		// TODO Auto-generated constructor stub
	} 

   
}
