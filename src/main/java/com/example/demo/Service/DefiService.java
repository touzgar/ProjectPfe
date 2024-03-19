package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.Model.Defi;


public interface DefiService {
	Defi saveDefi(Defi defi);
	Defi updateDefi(Long idDefi, String newMatchName, LocalDateTime newDateStart, String newResult);
	void deleteDefi(Defi defi);
	void deleteDefitById(Long iddefi);
	Defi getDefi(Long iddefi);
	List<Defi> getAllDefis();
	List<Defi> getHistoricalMatches();
	
}
