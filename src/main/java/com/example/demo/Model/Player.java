package com.example.demo.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayer;

    @Temporal(TemporalType.DATE)
    private Date contratEnd;

    @Temporal(TemporalType.DATE)
    private Date contratStart;

    private String countryOfResidence;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String discordId;

    private String inGameName;

    private String jerseySize;

    private String leagalefullname;

    private String mailAdress;

    private Double salary;

    private String socialMediaLinks;

    private String whatsappPhone;
   @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "team_id", nullable = true , referencedColumnName = "idteam") // This column will be added in the 'player' table to reference 'team'
    private Team team;

    // Getter and Setter for 'team'
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    @Transient
    private String teamName;

    public String getTeamName() {
        return team != null ? team.getTeamName() : null;
    }
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CompetencesAndHistorique> competencesAndHistoriques;
// Getter and Setter
       
     // Player can have zero or one contract
    @JsonManagedReference
    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    private ContractPlayer contractPlayer;
    
    public ContractPlayer getContractPlayer() {
        return contractPlayer;
    }
    public void setContractPlayer(ContractPlayer contractPlayer) {
        this.contractPlayer = contractPlayer;
        contractPlayer.setPlayer(this); 
    }
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AchievementPlayer> achievements;

  
  

    // Other methods such as equals(), hashCode() and toString() (optional)
}
