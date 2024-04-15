package com.example.demo.Model;

import java.util.Date;

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
public class Depense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long depenseId;
	private String depenseName;
	private Date date;
	private Double montant;
	private String description;
	
	@ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;
}
