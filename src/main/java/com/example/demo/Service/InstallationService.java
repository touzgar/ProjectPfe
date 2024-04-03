package com.example.demo.Service;

import java.util.List;


import com.example.demo.Model.Installation;


public interface InstallationService {
	Installation saveInstallation(Installation installation);
	Installation UpdateInstallation(Installation installation);
	void deleteInstallation(Installation installation);
	 void deleteInstallationById(Long idInstallation);
	Installation getInstallation(Long idInstallation);
	List<Installation> getAllInstallation();
	List<Installation> searchByInstallationName(String installationName);

	
}
