package com.example.demo.Service;

import java.util.List;


import com.example.demo.Model.Sponsor;

public interface SponsorService {
	Sponsor saveSponsor(Sponsor sponsor);
	Sponsor UpdateSponsor(Sponsor sponsor);
	void deleteSponsor(Sponsor sponsor);
	void deleteSponsorById(Long idSponsor);
	Sponsor getSponsor(Long idSponsor);
	List<Sponsor> getAllSponsors();
	List<Sponsor> searchBySponsorName(String sponsorName);

}
