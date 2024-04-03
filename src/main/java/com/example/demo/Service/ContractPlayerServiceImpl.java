package com.example.demo.Service;

import com.example.demo.Model.AchievementPlayer;
import com.example.demo.Model.ContractPlayer;
import com.example.demo.Model.Player;
import com.example.demo.Repository.ContractPlayerRepository;
import com.example.demo.Repository.PlayerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ContractPlayerServiceImpl implements ContractPlayerService {

    @Autowired
    ContractPlayerRepository contractPlayerRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Override
    public ContractPlayer createContractPlayer(ContractPlayer contractPlayer) {
        if (contractPlayer.getPlayer() == null) {
            throw new RuntimeException("No player information provided.");
        }
        String leagalefullname = contractPlayer.getPlayer().getLeagalefullname();
        Player player = playerRepository.findFirstByLeagalefullnameIgnoreCase(leagalefullname)
                .orElseThrow(() -> new RuntimeException("Player not found with name: " + leagalefullname));
        contractPlayer.setPlayer(player);
        return contractPlayerRepository.save(contractPlayer);
    }


    @Override
    public ContractPlayer saveContractPlayer(ContractPlayer contractPlayer) {
        return contractPlayerRepository.save(contractPlayer);
    }

    @Override
    public ContractPlayer updateContractPlayer(ContractPlayer contractPlayer) {
        return contractPlayerRepository.save(contractPlayer);
    }

    @Override
    public void deleteContractPlayer(ContractPlayer contractPlayer) {
        contractPlayerRepository.delete(contractPlayer);
    }

    @Override
    @Transactional
    public void deleteContractPlayerById(Long idContractPlayer) {
        contractPlayerRepository.findById(idContractPlayer)
                .ifPresent(contractPlayerRepository::delete);
    }
    @Override
    public ContractPlayer getContractPlayer(Long idContractPlayer) {
        return contractPlayerRepository.findById(idContractPlayer).orElse(null);
    }

    @Override
    public List<ContractPlayer> getAllContractPlayers() {
        return contractPlayerRepository.findAll();
    }
    @Override
	public List<ContractPlayer> searchByPlayerName(String playerName) {
		return contractPlayerRepository.findByPlayer_LeagalefullnameIgnoreCase(playerName);
	}


	@Override
	public ContractPlayer saveContractNameWithPlayerName(ContractPlayer contractPlayer, String leagalefullname) {
		return playerRepository.findFirstByLeagalefullnameIgnoreCase(leagalefullname).map(player -> {
	        contractPlayer.setPlayer(player);
	        return contractPlayerRepository.save(contractPlayer);
	    }).orElseThrow(() -> new RuntimeException("Player with name '" + leagalefullname + "' not found"));

	}
}
