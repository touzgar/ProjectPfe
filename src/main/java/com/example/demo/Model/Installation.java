package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Installation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int installationId;
    private String installationName;
    private String type;
    private boolean disponibilite;
    private int capacite;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTeam")
    private Team team;


	
}
