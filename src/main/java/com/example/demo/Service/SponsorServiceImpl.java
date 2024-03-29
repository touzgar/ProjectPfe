package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Sponsor;
import com.example.demo.Repository.SponsorRepository;

@Service
public class SponsorServiceImpl implements SponsorService {

	@Autowired
	SponsorRepository sponsorRepository;
	
	
	@Override
	public Sponsor saveSponsor(Sponsor sponsor) {
	
		return sponsorRepository.save(sponsor);
	}

	@Override
	public Sponsor UpdateSponsor(Sponsor sponsor) {
		return sponsorRepository.save(sponsor);
	}

	@Override
	public void deleteSponsor(Sponsor sponsor) {
		sponsorRepository.delete(sponsor);
	}

	@Override
	public void deleteSponsorById(Long idSponsor) {
		sponsorRepository.deleteById(idSponsor);
		
	}

	@Override
	public Sponsor getSponsor(Long idSponsor) {
		
		return sponsorRepository.findById(idSponsor).get();
	}

	@Override
	public List<Sponsor> getAllSponsors() {
		
		return sponsorRepository.findAll();
	}

	@Override
	public List<Sponsor> searchBySponsorName(String clubName) {
		// TODO Auto-generated method stub
		return null;
	}

}
