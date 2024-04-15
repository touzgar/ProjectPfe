package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Depense;
import com.example.demo.Repository.DepenseRepository;

@Service
public class DepenseServiceImpl implements DepenseService {
@Autowired
DepenseRepository depenseRepository;
	@Override
	public Depense saveDepense(Depense depense) {
		
		return depenseRepository.save(depense);
	}

	@Override
	public Depense UpdateDepense(Depense depense) {

		return depenseRepository.save(depense);
	}

	@Override
	public void deleteDepense(Depense depense) {
		depenseRepository.delete(depense);
		
	}

	@Override
	public void deleteDepenseById(Long idDepense) {
		depenseRepository.deleteById(idDepense);
		
	}

	@Override
	public Depense getDepense(Long idDepense) {
		
		return depenseRepository.findById(idDepense).get();
	}

	@Override
	public List<Depense> getAllDepenses() {
		
		return depenseRepository.findAll();
	}

}
