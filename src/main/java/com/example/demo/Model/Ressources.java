package com.example.demo.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "resourceId")

public class Ressources {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int resourceId;
		private String resourceName;
		
		@OneToMany(mappedBy = "ressources", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<Installation> installations;

	    @OneToMany(mappedBy = "ressources", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<Materiel> materiel;

	    @OneToMany(mappedBy = "ressources", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<Logiciel> logiciel;
	    
	    
	    
	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "team_id") // This is the foreign key column in the Ressources table.
	    private Team team;


}
