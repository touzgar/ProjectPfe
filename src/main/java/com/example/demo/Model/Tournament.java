package com.example.demo.Model;

import java.time.LocalDateTime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
	private LocalDateTime dateStart;
	private LocalDateTime dateEnd;
	private String Format;
	private Double PrizePool;
	private Boolean status;
	private int capacity;
	
	
	
	@ManyToMany(mappedBy = "tournaments")
	private List<Team> teams;

	 @OneToMany(mappedBy = "tournament",fetch = FetchType.EAGER)
	    private List<Defi> defi;	
}
