package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Defi;
import com.example.demo.Repository.DefiRepository;

@Service
public class DefiServiceImpl implements DefiService {
	@Autowired
	DefiRepository defiRepository;
	@Override
	public Defi saveDefi(Defi defi) {
		return defiRepository.save(defi);
	}

	@Override
	public Defi UpdateDefi(Defi defi) {
		return defiRepository.save(defi);
	}

	@Override
	public void deleteDefi(Defi defi) {
		defiRepository.delete(defi);
		
	}

	@Override
	public void deleteDefitById(Long iddefi) {
		defiRepository.deleteById(iddefi);
		
	}

	@Override
	public Defi getDefi(Long iddefi) {
	
		return defiRepository.findById(iddefi).get();
	}

	@Override
	public List<Defi> getAllDefis() {
		
		return defiRepository.findAll();
	}

}
