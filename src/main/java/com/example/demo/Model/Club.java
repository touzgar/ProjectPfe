package com.example.demo.Model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Club {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Temporal(TemporalType.DATE)
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
		
private Long idClub;
private String clubName;
private String description;
private Date dateCreation;


@OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Coach> coach;

// Getter and setter for coaches
public List<Coach> getCoach() {
    return coach;
}

public void setCoach(List<Coach> coach) {
    this.coach = coach;
}
@JsonIgnore

@OneToOne(mappedBy = "club", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
private Team team;


public Team getTeam() {
	return team;
}

public void setTeam(Team team) {
    // If there's an existing team, remove the club reference from it
    if (this.team != null) {
        this.team.setClub(null);
    }
    
    // Set the new team
    this.team = team;
    
    // If the new team is not null, set this club as its club
    if (team != null) {
        team.setClub(this);
    }
}


public Long getIdClub() {
	return idClub;
}
public void setIdClub(Long idClub) {
	this.idClub = idClub;
}
public String getClubName() {
	return clubName;
}
public void setClubName(String clubName) {
	this.clubName = clubName;
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
public Club() {
	super();
	
}

}
