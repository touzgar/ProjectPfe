package com.example.demo.Model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity

public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 @Temporal(TemporalType.DATE)
 @DateTimeFormat(pattern = "yyyy-MM-dd")
	
	private Long idTeam;
	private String teamName;
	private String description;
	private Date dateCreation;
	@ElementCollection
	private List<String> participatingTournaments;
	@ElementCollection
    private Map<Player, String> rolesAndResponsibilities;
	
	public Map<Player, String> getRolesAndResponsibilities() {
		return rolesAndResponsibilities;
	}
	public void setRolesAndResponsibilities(Map<Player, String> rolesAndResponsibilities) {
		this.rolesAndResponsibilities = rolesAndResponsibilities;
	}
	@JsonManagedReference
	
	
	 @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AchivementsTeam achivementsTeam;

    // Getters and setters for achivementsTeam

    public AchivementsTeam getAchivementsTeam() {
        return achivementsTeam;
    }

    public void setAchivementsTeam(AchivementsTeam achivementsTeam) {
        this.achivementsTeam = achivementsTeam;
    }
	
	 @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<Player> players;

	    // Getter and Setter for 'players'
	    public List<Player> getPlayers() {
	        return players;
	    }

	    public void setPlayers(List<Player> players) {
	        this.players = players;
	    }
	    
	    
	    
	
	@OneToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "club_id", referencedColumnName = "idClub")
	    private Club club;
	
	 public Club getClub() {
		 return club ;
	 }
	 public void setClub(Club club) {
		 this.club=club;
	 }
	 
	 
	
	 @ManyToOne
	    @JoinColumn(name = "coach_id") // references the primary key 'id' of Coach
	    private Coach coach; 
		    	 
    
	public Coach getCoach() {
		return coach;
	}
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	public Long getIdTeam() {
		return idTeam;
	}
	public void setIdTeam(Long idTeam) {
		this.idTeam = idTeam;
	}
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public List<String> getParticipatingTournaments() {
		return participatingTournaments;
	}
	public void setParticipatingTournaments(List<String> participatingTournaments) {
		this.participatingTournaments = participatingTournaments;
	}
	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
