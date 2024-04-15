package com.example.demo.Service;

import java.util.List;


import com.example.demo.Model.Depense;

public interface DepenseService {
	Depense saveDepense(Depense depense);
	Depense UpdateDepense(Depense depense);
	void deleteDepense(Depense depense);
	void deleteDepenseById(Long idDepense);
	Depense getDepense(Long idDepense);
	List<Depense> getAllDepenses();

}
