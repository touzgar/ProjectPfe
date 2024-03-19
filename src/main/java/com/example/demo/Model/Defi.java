package com.example.demo.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Defi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMatch;
	private String matchName;
	private LocalDateTime dateStart;
	private String result;
	
	@ManyToOne
    private Tournament tournament;
}
