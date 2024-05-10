package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Coach;
import com.example.demo.Model.Sponsor;
import com.example.demo.Model.SponsorContract;
import com.example.demo.Model.Team;
import com.example.demo.Model.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.SponsorContractRepository;
import com.example.demo.Repository.SponsorRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UserRepository;

@Service
public class SponsorContractServiceImpl implements SponsorContractService {
	 @Autowired
	    SponsorContractRepository sponsorContractRepository;
	    @Autowired
	    SponsorRepository sponsorRepository;
	    @Autowired
	    TeamRepository teamRepository;
	    @Autowired
	    UserRepository userRepository;
	    @Autowired
	    RoleRepository roleRepository;
	    
	    
	    @Override
	public SponsorContract saveSponsorContract(SponsorContract sponsorContract) {
		
		return sponsorContractRepository.save(sponsorContract);
	}

	@Override
	public SponsorContract UpdateSponsorContract(SponsorContract sponsorContract) {
		return sponsorContractRepository.save(sponsorContract);
	}

	@Override
	public void deleteSponsorContract(SponsorContract sponsorContract) {
			sponsorContractRepository.delete(sponsorContract);
		
	}

	@Override
	public void deleteSponsorContractById(Long idSponsorContract) {
		sponsorContractRepository.deleteById(idSponsorContract);
		
	}

	@Override
	public SponsorContract getSponsor(Long idSponsorContract) {
		
		return sponsorContractRepository.findById(idSponsorContract).get();
	}

	@Override
	public List<SponsorContract> getAllSponsorContract() {
		return sponsorContractRepository.findAll();
	}

	@Override
	public List<SponsorContract> searchBySponsorContractName(String SponsorContractName) {
		return sponsorContractRepository.findBySponsorContractNameContainingIgnoreCase(SponsorContractName);
	}
	/*@Override
	public SponsorContract addSponsorToSponsorContract(String sponsorContractName, String sponsorName) {
	    // Find SponsorContract by name
	    SponsorContract sponsorContract = sponsorContractRepository.findBySponsorContractNameContainingIgnoreCase(sponsorContractName)
	                    .stream()
	                    .findFirst()
	                    .orElseThrow(() -> new RuntimeException("SponsorContract with name '" + sponsorContractName + "' not found"));

	    // Find Sponsor by name
	    Sponsor sponsor = sponsorRepository.findBySponsorNameContainingIgnoreCase(sponsorName)
	                    .stream()
	                    .findFirst()
	                    .orElseThrow(() -> new RuntimeException("Sponsor with name '" + sponsorName + "' not found"));

	    // Link SponsorContract with Sponsor
	    sponsorContract.setSponsor(sponsor); // Assuming a SponsorContract is linked to one Sponsor

	    // If you also need to maintain a collection of SponsorContracts in the Sponsor, add the SponsorContract to the list
	    if (sponsor.getSponsorContracts() == null) {
	        sponsor.setSponsorContracts(new ArrayList<>());
	    }
	    sponsor.getSponsorContracts().add(sponsorContract);

	    // Save the changes
	    sponsorContractRepository.save(sponsorContract);
	    sponsorRepository.save(sponsor);

	    return sponsorContract;
	}
*/
	@Override
    public SponsorContract addSponsorContractWithDetails(SponsorContract sponsorContract, String sponsorUsername, String teamName) {
        // Find or create Sponsor entity
        User sponsorUser = userRepository.findByUsername(sponsorUsername);
        if (sponsorUser == null || !sponsorUser.getRoles().stream().anyMatch(r -> r.getRole().equalsIgnoreCase("Sponsor"))) {
            throw new RuntimeException("User with role 'Sponsor' and username '" + sponsorUsername + "' not found");
        }

        Sponsor sponsor = sponsorRepository.findBySponsorNameContainingIgnoreCase(sponsorUsername)
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Sponsor newSponsor = new Sponsor();
                    newSponsor.setSponsorName(sponsorUsername);
                    return sponsorRepository.save(newSponsor);
                });

        // Find Team by name
        Team team = teamRepository.findByTeamNameContainingIgnoreCase(teamName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));

        // Set Sponsor and Team to SponsorContract
        sponsorContract.setSponsor(sponsor);
        sponsorContract.setTeam(team);

        // Save the SponsorContract
        return sponsorContractRepository.save(sponsorContract);
    }
	
	 @Override
	    public SponsorContract updateSponsorContractWithDetails(SponsorContract sponsorContract, String sponsorUsername, String teamName) {
	        // Ensure the existing sponsor contract is found
	        SponsorContract existingContract = sponsorContractRepository.findById(sponsorContract.getIdSponsorContract())
	                .orElseThrow(() -> new RuntimeException("SponsorContract not found with id: " + sponsorContract.getIdSponsorContract()));

	        // Update the attributes
	        existingContract.setSponsorContractName(sponsorContract.getSponsorContractName());
	        existingContract.setDateStart(sponsorContract.getDateStart());
	        existingContract.setDateEnd(sponsorContract.getDateEnd());
	        existingContract.setObjectif(sponsorContract.getObjectif());

	        // Link with the sponsor and team
	        return linkSponsorContractWithSponsorAndTeam(existingContract, sponsorUsername, teamName);
	    }

	    private SponsorContract linkSponsorContractWithSponsorAndTeam(SponsorContract sponsorContract, String sponsorUsername, String teamName) {
	        // Find or create Sponsor entity
	        User sponsorUser = userRepository.findByUsername(sponsorUsername);
	        if (sponsorUser == null || !sponsorUser.getRoles().stream().anyMatch(r -> r.getRole().equalsIgnoreCase("Sponsor"))) {
	            throw new RuntimeException("User with role 'Sponsor' and username '" + sponsorUsername + "' not found");
	        }

	        Sponsor sponsor = sponsorRepository.findBySponsorNameContainingIgnoreCase(sponsorUsername)
	                .stream()
	                .findFirst()
	                .orElseGet(() -> {
	                    Sponsor newSponsor = new Sponsor();
	                    newSponsor.setSponsorName(sponsorUsername);
	                    return sponsorRepository.save(newSponsor);
	                });

	        // Find Team by name
	        Team team = teamRepository.findByTeamNameContainingIgnoreCase(teamName)
	                .stream()
	                .findFirst()
	                .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));

	        // Set Sponsor and Team to SponsorContract
	        sponsorContract.setSponsor(sponsor);
	        sponsorContract.setTeam(team);

	        // Save the SponsorContract
	        return sponsorContractRepository.save(sponsorContract);
	    }



}
