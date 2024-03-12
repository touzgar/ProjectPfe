package com.example.demo.Service;

import com.example.demo.Model.CompetencesAndHistorique;
import com.example.demo.Model.Player;
import com.example.demo.Repository.CompetencesAndHistoriqueRepository;
import com.example.demo.Repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompetencesAndHistoriqueServiceImpl implements CompetencesAndHistoriqueService {

    @Autowired
    CompetencesAndHistoriqueRepository repository;
    @Autowired
    PlayerRepository playerRepository; 
    @Override
    public CompetencesAndHistorique saveCompetenceAndHistorique(CompetencesAndHistorique competencesAndHistorique, String playerName) {
        Player player = playerRepository.findFirstByLeagalefullnameIgnoreCase(playerName)
            .orElseThrow(() -> new RuntimeException("Player not found with name: " + playerName));
        competencesAndHistorique.setPlayer(player);
        return repository.save(competencesAndHistorique);
    }
    @Override
    public CompetencesAndHistorique updateCompetenceAndHistorique(CompetencesAndHistorique competencesAndHistorique) {
        return repository.save(competencesAndHistorique);
    }

    @Override
    public void deleteCompetenceAndHistoriqueById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CompetencesAndHistorique getCompetenceAndHistoriqueById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<CompetencesAndHistorique> getAllCompetencesAndHistoriques() {
        return repository.findAll();
    }
}
