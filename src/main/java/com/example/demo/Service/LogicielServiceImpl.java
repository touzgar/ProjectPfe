package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.Installation;
import com.example.demo.Model.Logiciel;
import com.example.demo.Repository.LogicielRepository;

@Service
public class LogicielServiceImpl implements LogicielService {
@Autowired
LogicielRepository logicielRepository;
	@Override
	public Logiciel saveLogiciel(Logiciel logiciel) {
		
		return logicielRepository.save(logiciel);
	}

	@Override
	public Logiciel UpdateLogiciel(Logiciel logiciel) {
		
		return logicielRepository.save(logiciel);
	}

	@Override
	public void deleteLogiciel(Logiciel logiciel) {
		logicielRepository.delete(logiciel);
	}

	@Override
	public void deleteLogicielById(Long idLogiciel) {
		logicielRepository.deleteById(idLogiciel);
		
	}

	@Override
	public Logiciel getLogiciel(Long idLogiciel) {
		return logicielRepository.findById(idLogiciel).get();
		
	}

	@Override
	public List<Logiciel> getAllLogiciel() {
		
		return logicielRepository.findAll();
		
	}

	@Override
	public List<Logiciel> searchByLogicielName(String logicielName) {

		return logicielRepository.findByLogicielNameContainingIgnoreCase(logicielName);
	}
	

}
