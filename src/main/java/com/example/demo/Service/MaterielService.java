package com.example.demo.Service;

import java.util.List;


import com.example.demo.Model.Materiel;

public interface MaterielService {
	Materiel saveMateriel(Materiel materiel);
	Materiel UpdateMateriel(Materiel materiel);
	void deleteMateriel(Materiel materiel);
	void deleteMaterielById(Long idMateriel);
	Materiel getMateriel(Long idMateriel);
	List<Materiel> getAllMateriel();
	
}
