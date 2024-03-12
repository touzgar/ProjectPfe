package com.example.demo.Model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class AchievementPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAchievementPlayer;
    private String playerName;
    private List<String> trophie;
    private Date dateAchievement;
    private Boolean status;
    
    @OneToOne
    @JoinColumn(name = "player_id", nullable = true)
    @JsonBackReference
    private Player player;
    
 
    
   
    
    
    
	public Long getIdAchievementPlayer() {
		return idAchievementPlayer;
	}
	public void setIdAchievementPlayer(Long idAchievementPlayer) {
		this.idAchievementPlayer = idAchievementPlayer;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public List<String> getTrophie() {
		return trophie;
	}
	public void setTrophie(List<String> trophie) {
		this.trophie = trophie;
	}
	public Date getDateAchievement() {
		return dateAchievement;
	}
	public void setDateAchievement(Date dateAchievement) {
		this.dateAchievement = dateAchievement;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
	public AchievementPlayer(Long idAchievementPlayer, String playerName, List<String> trophie, Date dateAchievement,
			Boolean status) {
		super();
		this.idAchievementPlayer = idAchievementPlayer;
		this.playerName = playerName;
		this.trophie = trophie;
		this.dateAchievement = dateAchievement;
		this.status = status;
	}
	public AchievementPlayer() {
		super();
	
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	

   
}