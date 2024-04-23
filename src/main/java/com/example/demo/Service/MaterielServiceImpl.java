package com.example.demo.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Logiciel;
import com.example.demo.Model.Materiel;
import com.example.demo.Repository.MaterielRepository;

@Service
public class MaterielServiceImpl implements MaterielService {

    @Autowired
    private MaterielRepository materielRepository;

    @Override
    public Materiel saveMateriel(Materiel materiel) {
        return materielRepository.save(materiel);
    }

    @Override
    public Materiel UpdateMateriel(Materiel materiel) {
        // Assuming that you only want to update an existing materiel.
        // You should check whether the materiel exists.
        return materielRepository.save(materiel);
    }

    @Override
    public void deleteMateriel(Materiel materiel) {
        materielRepository.delete(materiel);
    }

   
    @Override
    @Transactional
    public void deleteMaterielById(Long idMateriel) {
        // Check if the installation exists
        Materiel materiel = materielRepository.findById(idMateriel)
                .orElseThrow(() -> new RuntimeException("Materiel not found for this id :: " + idMateriel));

        // Remove the installation from its associated team, if any
        if (materiel.getTeam() != null) {
            materiel.getTeam().getMateriel().remove(materiel);
        }

        // Delete the installation
        materielRepository.delete(materiel);
    }


    @Override
    public Materiel getMateriel(Long idMateriel) {
        // Or use .orElseThrow() to throw an exception if the materiel is not found
        return materielRepository.findById(idMateriel).get();
    }

    @Override
    public List<Materiel> getAllMateriel() {
        return materielRepository.findAll();
    }

	@Override
	public List<Materiel> searchByMaterielName(String materielName) {
		
		return materielRepository.findByMaterielNameContainingIgnoreCase(materielName);
	}
}
