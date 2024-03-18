package com.example.demo.Model;





import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Coach {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idCoach;
private String nameCoach;
private String email;
private String Rapport;

@JsonIgnore
@ManyToOne
@JoinColumn(name = "club_id")
private Club club;

// Getter and setter for club
public Club getClub() {
    return club;
}

public void setClub(Club club) {
    this.club = club;
}
@Transient // This field is not persisted
private String clubName;

// Getter and setter for clubName
public String getClubName() {
    if (this.club != null) {
        return this.club.getClubName();
    } else {
        return this.clubName; // This will be null since it's transient and not stored in the database
    }
}

public void setClubName(String clubName) {
    this.clubName = clubName;
}

@ManyToMany(mappedBy = "coaches")
@JsonIgnore
private List<Team> teams;

// Getter and setter for teams
public List<Team> getTeams() {
    return teams;
}

public void setTeams(List<Team> teams) {
    this.teams = teams;
}


public Long getIdCoach() {
	return idCoach;
}
public void setIdCoach(Long idCoach) {
	this.idCoach = idCoach;
}
public String getNameCoach() {
	return nameCoach;
}
public void setNameCoach(String nameCoach) {
	this.nameCoach = nameCoach;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getRapport() {
	return Rapport;
}
public void setRapport(String rapport) {
	Rapport = rapport;
}
public Coach() {
	super();
	// TODO Auto-generated constructor stub
}






}
