package com.example.demo.Service;

import com.example.demo.Model.CompetencesAndHistorique;
import java.util.List;

public interface CompetencesAndHistoriqueService {
    CompetencesAndHistorique saveCompetenceAndHistorique(CompetencesAndHistorique competencesAndHistorique, String playerName);
    CompetencesAndHistorique updateCompetenceAndHistorique(CompetencesAndHistorique competencesAndHistorique);
    void deleteCompetenceAndHistoriqueById(Long id);
    CompetencesAndHistorique getCompetenceAndHistoriqueById(Long id);
    List<CompetencesAndHistorique> getAllCompetencesAndHistoriques();
}
