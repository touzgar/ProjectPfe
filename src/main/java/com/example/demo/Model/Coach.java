package com.example.demo.Model;






import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idCoach")
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

@OneToMany(mappedBy = "coach")
private List<SessionTraining> sessionTrainings;







}
