package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Installation;
import com.example.demo.Repository.InstallationsRepository;

@Service
public class InstallationServiceImpl implements InstallationService {
@Autowired
InstallationsRepository installationsRepository;
	@Override
	public Installation saveInstallation(Installation installation) {
		return installationsRepository.save(installation);
	}

	@Override
	public Installation UpdateInstallation(Installation installation) {
		return installationsRepository.save(installation);
	}

	@Override
	public void deleteInstallation(Installation installation) {
		 installationsRepository.delete(installation);
		
	}

	 @Override
	    @Transactional
	    public void deleteInstallationById(Long idInstallation) {
	        // Check if the installation exists
	        Installation installation = installationsRepository.findById(idInstallation)
	                .orElseThrow(() -> new RuntimeException("Installation not found for this id :: " + idInstallation));

	        // Remove the installation from its associated team, if any
	        if (installation.getTeam() != null) {
	            installation.getTeam().getInstallations().remove(installation);
	        }

	        // Delete the installation
	        installationsRepository.delete(installation);
	    }
	@Override
	public Installation getInstallation(Long idInstallation) {
		
		return installationsRepository.findById(idInstallation).get();
	}

	@Override
	public List<Installation> getAllInstallation() {
		
		return installationsRepository.findAll();
	}
	// In RessourceServiceImpl.java

	@Override
	public List<Installation> searchByInstallationName(String installationName) {
		
		return installationsRepository.findByInstallationNameContainingIgnoreCase(installationName);
	}
	


}
