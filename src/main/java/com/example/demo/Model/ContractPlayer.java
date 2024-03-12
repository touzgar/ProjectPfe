package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ContractPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContractPlayer;
    private String detailsContractuels;
    private String termesFinanciers;
    private String clausesSpecifiques;
    private List<String> objectifs;
    private Date date;

    @OneToOne
    @JoinColumn(name = "player_id", nullable = true)
    @JsonBackReference
    private Player player;

    
    
    
    
    
    

    public ContractPlayer() {
    }

    public ContractPlayer(Long idContractPlayer, String detailsContractuels, String termesFinanciers,
                          String clausesSpecifiques, List<String> objectifs, Date date, Player player) {
        this.idContractPlayer = idContractPlayer;
        this.detailsContractuels = detailsContractuels;
        this.termesFinanciers = termesFinanciers;
        this.clausesSpecifiques = clausesSpecifiques;
        this.objectifs = objectifs;
        this.date = date;
        this.player = player;
    }

    // Getters and setters
    public Long getIdContractPlayer() {
        return idContractPlayer;
    }

    public void setIdContractPlayer(Long idContractPlayer) {
        this.idContractPlayer = idContractPlayer;
    }

    public String getDetailsContractuels() {
        return detailsContractuels;
    }

    public void setDetailsContractuels(String detailsContractuels) {
        this.detailsContractuels = detailsContractuels;
    }

    public String getTermesFinanciers() {
        return termesFinanciers;
    }

    public void setTermesFinanciers(String termesFinanciers) {
        this.termesFinanciers = termesFinanciers;
    }

    public String getClausesSpecifiques() {
        return clausesSpecifiques;
    }

    public void setClausesSpecifiques(String clausesSpecifiques) {
        this.clausesSpecifiques = clausesSpecifiques;
    }

    public List<String> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<String> objectifs) {
        this.objectifs = objectifs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}