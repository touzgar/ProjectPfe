package com.example.demo.Model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.InheritanceType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idSession")
@Inheritance(strategy = InheritanceType.JOINED)
public class SessionTraining {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long idSession;
		private String sessionName; 
		private LocalDateTime dateStart;
		private LocalDateTime dateEnd;

		private List<String> objectifs;
		private String feedbacksEntraineurs;
		
		@ManyToOne
	    @JoinColumn(name = "coach_id")
	    private Coach coach;
		
	    @ManyToMany
	    private List<Player> presencePlayer;

}
