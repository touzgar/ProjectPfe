package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Ressources;
import com.example.demo.Repository.RessourceRepository;

@Service
public class RessourceServiceImpl implements RessourceService {
@Autowired
RessourceRepository ressourceRepository;
	@Override
	public Ressources saveRessource(Ressources ressource) {
		return ressourceRepository.save(ressource);
	}

	@Override
	public Ressources UpdateRessource(Ressources ressource) {
		return ressourceRepository.save(ressource);

	}

	@Override
	public void deleteRessource(Ressources ressource) {
		ressourceRepository.delete(ressource);
		
	}

	@Override
	public void deleteRessourceById(Long idRessource) {
		ressourceRepository.deleteById(idRessource);
		
	}

	@Override
	public Ressources getRessource(Long idRessource) {
		
		return ressourceRepository.findById(idRessource).get();
	}

	@Override
	public List<Ressources> getAllRessources() {
		
		return ressourceRepository.findAll();
	}

}
