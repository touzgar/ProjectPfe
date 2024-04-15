package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Revenus;
import com.example.demo.Repository.RevenusRepository;

@Service
public class RevenusServiceImpl implements RevenusService  {
@Autowired
RevenusRepository revenusRepository; 
	@Override
	public Revenus saveRevenus(Revenus revenus) {
		
		return revenusRepository.save(revenus);
	}

	@Override
	public Revenus UpdateRevenus(Revenus revenus) {
		return revenusRepository.save(revenus);	}

	@Override
	public void deleteRevenus(Revenus revenus) {
		revenusRepository.delete(revenus);
	}

	@Override
	public void deleteRevenusById(Long idRevenus) {
		revenusRepository.deleteById(idRevenus);
		
	}

	@Override
	public Revenus getRevenus(Long idRevenus) {
		
		return revenusRepository.findById(idRevenus).get();
	}

	@Override
	public List<Revenus> getAllRevenuss() {
		
		return revenusRepository.findAll();
	}

}
