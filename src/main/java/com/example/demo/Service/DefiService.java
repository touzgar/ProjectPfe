package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Defi;


public interface DefiService {
	Defi saveDefi(Defi defi);
	Defi UpdateDefi(Defi defi);
	void deleteDefi(Defi defi);
	void deleteDefitById(Long iddefi);
	Defi getDefi(Long iddefi);
	List<Defi> getAllDefis();
	
}
