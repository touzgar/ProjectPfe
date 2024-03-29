package com.example.demo.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
		  property = "idSponsor")
public class Sponsor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long idSponsor;
	    private String detailContractuels;
	    private String termesFinancieres;
	    private String sponsorName;
	    
	    @ManyToMany
	    @JoinTable(
	        name = "sponsor_team",
	        joinColumns = @JoinColumn(name = "sponsor_id"),
	        inverseJoinColumns = @JoinColumn(name = "team_id")
	    )
	    private List<Team> teams;
	    
	    @OneToMany(mappedBy = "sponsor")
	    private List<SponsorContract> sponsorContracts;
	    
	    
}
