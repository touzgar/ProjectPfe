package com.example.demo.Service;

import java.util.List;


import com.example.demo.Model.SponsorContract;
import com.example.demo.Model.Team;

public interface SponsorContractService {
	SponsorContract saveSponsorContract(SponsorContract sponsorContract);
	SponsorContract UpdateSponsorContract(SponsorContract sponsorContract);
	void deleteSponsorContract(SponsorContract sponsorContract);
	void deleteSponsorContractById(Long idSponsorContract);
	SponsorContract getSponsor(Long idSponsorContract);
	List<SponsorContract> getAllSponsorContract();
	List<SponsorContract> searchBySponsorContractName(String SponsorContractName);
	SponsorContract addSponsorToSponsorContract(String sponsorContractName, String sponsorName);

}
