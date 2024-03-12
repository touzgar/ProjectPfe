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

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    
    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private AchievementPlayer AchievementPlayer;
    


	    
	public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }
    

    // Include getters and setters for all other fields

    public Date getContratEnd() {
		return contratEnd;
	}

	public void setContratEnd(Date contratEnd) {
		this.contratEnd = contratEnd;
	}

	public Date getContratStart() {
		return contratStart;
	}

	public void setContratStart(Date contratStart) {
		this.contratStart = contratStart;
	}

	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDiscordId() {
		return discordId;
	}

	public void setDiscordId(String discordId) {
		this.discordId = discordId;
	}

	public String getInGameName() {
		return inGameName;
	}

	public void setInGameName(String inGameName) {
		this.inGameName = inGameName;
	}

	public String getJerseySize() {
		return jerseySize;
	}

	public void setJerseySize(String jerseySize) {
		this.jerseySize = jerseySize;
	}

	public String getLeagalefullname() {
		return leagalefullname;
	}

	public void setLeagalefullname(String leagalefullname) {
		this.leagalefullname = leagalefullname;
	}

	public String getMailAdress() {
		return mailAdress;
	}

	public void setMailAdress(String mailAdress) {
		this.mailAdress = mailAdress;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getSocialMediaLinks() {
		return socialMediaLinks;
	}

	public void setSocialMediaLinks(String socialMediaLinks) {
		this.socialMediaLinks = socialMediaLinks;
	}

	public String getWhatsappPhone() {
		return whatsappPhone;
	}

	public void setWhatsappPhone(String whatsappPhone) {
		this.whatsappPhone = whatsappPhone;
	}

	// No-args constructor
    public Player() {
    }

    // All-args constructor (optional, for convenience)
    public Player(Long idPlayer, Date contratEnd, Date contratStart, String countryOfResidence,
                  Date dateOfBirth, String discordId, String inGameName, String jerseySize,
                  String leagalefullname, String mailAdress, Double salary, String socialMediaLinks,
                  String whatsappPhone) {
        this.idPlayer = idPlayer;
        this.contratEnd = contratEnd;
        this.contratStart = contratStart;
        this.countryOfResidence = countryOfResidence;
        this.dateOfBirth = dateOfBirth;
        this.discordId = discordId;
        this.inGameName = inGameName;
        this.jerseySize = jerseySize;
        this.leagalefullname = leagalefullname;
        this.mailAdress = mailAdress;
        this.salary = salary;
        this.socialMediaLinks = socialMediaLinks;
        this.whatsappPhone = whatsappPhone;
    }

    // Other methods such as equals(), hashCode() and toString() (optional)
}
