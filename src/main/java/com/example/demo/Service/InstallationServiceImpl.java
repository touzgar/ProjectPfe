package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void deleteInstallationById(Long idInstallation) {
		installationsRepository.deleteById(idInstallation);
		
	}

	@Override
	public Installation getInstallation(Long idInstallation) {
		
		return installationsRepository.findById(idInstallation).get();
	}

	@Override
	public List<Installation> getAllInstallation() {
		
		return installationsRepository.findAll();
	}

}
