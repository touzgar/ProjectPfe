package com.example.demo.Model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idSponsorContract")
public class SponsorContract {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long idSponsorContract;
	private String sponsorContractName;

	private Date dateStart;
	private Date dateEnd;
	private String objectif;

	@ManyToOne
    @JoinColumn(name = "sponsor_id") // This column is added to SponsorContract table.
    private Sponsor sponsor;
	
	
}
