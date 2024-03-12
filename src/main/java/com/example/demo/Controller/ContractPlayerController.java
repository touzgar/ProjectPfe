package com.example.demo.Controller;

import com.example.demo.Model.ContractPlayer;
import com.example.demo.Service.ContractPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractPlayer")
@CrossOrigin("*")
public class ContractPlayerController {

    @Autowired
    ContractPlayerService contractPlayerService;

    @GetMapping("/getAll")
    public List<ContractPlayer> getAllContractPlayers() {
        return contractPlayerService.getAllContractPlayers();
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ContractPlayer> getContractPlayerById(@PathVariable Long id) {
        ContractPlayer contractPlayer = contractPlayerService.getContractPlayer(id);
        return contractPlayer != null ? ResponseEntity.ok(contractPlayer) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<ContractPlayer> createContractPlayer(@RequestBody ContractPlayer contractPlayer) {
        ContractPlayer newContractPlayer = contractPlayerService.createContractPlayer(contractPlayer);
        return ResponseEntity.ok(newContractPlayer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ContractPlayer> updateContractPlayer(@PathVariable Long id, @RequestBody ContractPlayer contractPlayerDetails) {
        ContractPlayer existingContractPlayer = contractPlayerService.getContractPlayer(id);
        if (existingContractPlayer != null) {
            // Update fields from contractPlayerDetails to existingContractPlayer
            existingContractPlayer.setDetailsContractuels(contractPlayerDetails.getDetailsContractuels());
            existingContractPlayer.setTermesFinanciers(contractPlayerDetails.getTermesFinanciers());
            existingContractPlayer.setClausesSpecifiques(contractPlayerDetails.getClausesSpecifiques());
            existingContractPlayer.setObjectifs(contractPlayerDetails.getObjectifs());
            existingContractPlayer.setDate(contractPlayerDetails.getDate());

            // Save the updated contractPlayer
            ContractPlayer updatedContractPlayer = contractPlayerService.updateContractPlayer(existingContractPlayer);
            return ResponseEntity.ok(updatedContractPlayer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContractPlayer(@PathVariable Long id) {
        contractPlayerService.deleteContractPlayerById(id);
        return ResponseEntity.ok().build();
    }
}