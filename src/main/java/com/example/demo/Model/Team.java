package com.example.demo.Model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idTeam")

public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 @Temporal(TemporalType.DATE)
 @DateTimeFormat(pattern = "yyyy-MM-dd")
	
	private Long idTeam;
	private String teamName;
	private String description;
	private Date dateCreation;
	@ElementCollection
    private Map<Player, String> rolesAndResponsibilities;
	
	public Map<Player, String> getRolesAndResponsibilities() {
		return rolesAndResponsibilities;
	}
	public void setRolesAndResponsibilities(Map<Player, String> rolesAndResponsibilities) {
		this.rolesAndResponsibilities = rolesAndResponsibilities;
	}
	
	 @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	    private List<Player> players;

	    // Getter and Setter for 'players'
	    public List<Player> getPlayers() {
	        return players;
	    }

	    public void setPlayers(List<Player> players) {
	        this.players = players;
	    }
	    
	    
	    
	    
	
	    @ManyToOne
	    @JoinColumn(name = "club_id")
	    @JsonBackReference
	    private Club club;	
	 public Club getClub() {
		 return club ;
	 }
	 public void setClub(Club club) {
		 this.club=club;
	 }
	 
	 @ManyToMany
	    @JoinTable(
	        name = "team_coach",
	        joinColumns = @JoinColumn(name = "team_id"),
	        inverseJoinColumns = @JoinColumn(name = "coach_id")
	    )
	    @JsonManagedReference
	    private List<Coach> coaches;

	    // Getter and setter for coaches
	    public List<Coach> getCoaches() {
	        return coaches;
	    }

	    public void setCoaches(List<Coach> coaches) {
	        this.coaches = coaches;
	    }

	
	    @ManyToMany
	    @JoinTable(
	        name = "team_tournament", // Specifies the join table name
	        joinColumns = @JoinColumn(name = "team_id"), // Specifies the column for team ID in the join table
	        inverseJoinColumns = @JoinColumn(name = "tournament_id") // Specifies the column for tournament ID in the join table
	    )
	    private List<Tournament> tournaments;
	    
	    
	    @ManyToMany(mappedBy = "teams")
	    private List<Sponsor> sponsors;

	    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Ressources> ressources;
	    
	    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
	    private List<AchivementTeam> achievementTeams;

}
