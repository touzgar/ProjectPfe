package com.example.demo.Service;

import java.util.List;


import com.example.demo.Model.Logiciel;


public interface LogicielService {
	Logiciel saveLogiciel(Logiciel logiciel);
	Logiciel UpdateLogiciel(Logiciel logiciel);
	void deleteLogiciel(Logiciel logiciel);
	void deleteLogicielById(Long idLogiciel);
	Logiciel getLogiciel(Long idLogiciel);
	List<Logiciel> getAllLogiciel();
	List<Logiciel> searchByLogicielName(String logicielName);

}
