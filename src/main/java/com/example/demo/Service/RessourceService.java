package com.example.demo.Service;

import java.util.List;


import com.example.demo.Model.Ressources;


public interface RessourceService {
	Ressources saveRessource(Ressources ressource);
	Ressources UpdateRessource(Ressources ressource);
	void deleteRessource(Ressources ressource);
	void deleteRessourceById(Long idRessource);
	Ressources getRessource(Long idRessource);
	List<Ressources> getAllRessources();
	// In RessourceService.java
	Ressources findRessourcesByName(String resourceName);
	List<Ressources> searchByRessourcesName(String ressourcesName);

	
}
