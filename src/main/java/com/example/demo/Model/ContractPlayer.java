package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idContractPlayer")

public class ContractPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContractPlayer;
    private String detailsContractuels;
    private String termesFinanciers;
    private String clausesSpecifiques;
    private List<String> objectifs;
    private Date dateStart;
    private Date dateEnd;
    private String ContractName; 

    @OneToOne
    @JoinColumn(name = "player_id", nullable = true)
    private Player player;
    
    
    
    
    
    

   }