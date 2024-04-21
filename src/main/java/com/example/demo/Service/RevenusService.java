package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import com.example.demo.Model.Revenus;

public interface RevenusService {
	Revenus saveRevenus(Revenus revenus);
	Revenus UpdateRevenus(Revenus revenus);
	void deleteRevenus(Revenus revenus);
	void deleteRevenusById(Long idRevenus);
	Revenus getRevenus(Long idRevenus);
	List<Revenus> getAllRevenuss();
	Double calculateTotalRevenus();
	Map<String, Double> calculateTotalRevenusByMonth();

}
