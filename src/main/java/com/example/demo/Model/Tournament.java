package com.example.demo.Model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idTournament")
public class Tournament {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTournament;
	private String tournamentName;
	private Date dateStart;
	private Date dateEnd;
	private String Format;
	private Double PrizePool;
	private Boolean status;
	
	
	
	@ManyToMany(mappedBy = "tournaments")
	private List<Team> teams;

	// Getter and Setter for teams
		
}
