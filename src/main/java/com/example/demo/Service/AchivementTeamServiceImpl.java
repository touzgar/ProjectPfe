package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.AchivementsTeam;
import com.example.demo.Model.Team;
import com.example.demo.Repository.AchievementTeamRepository;
import com.example.demo.Repository.TeamRepository;

import jakarta.transaction.Transactional;



@Service
public class AchivementTeamServiceImpl implements AchivementTeamService {
	@Autowired
	AchievementTeamRepository achievementTeamRepository;
	 @Autowired
	 TeamRepository teamRepository;
	@Override
	public AchivementsTeam saveAchivementsTeam(AchivementsTeam achivement) {
		return achievementTeamRepository.save(achivement);
	}

	
	@Override
	@Transactional
	public AchivementsTeam updateAchivementsTeam(Long achivementId, AchivementsTeam updatedDetails) {
	    AchivementsTeam achievement = achievementTeamRepository.findById(achivementId)
	        .orElseThrow(() -> new RuntimeException("Achievement not found for this id :: " + achivementId));

	    // Directly setting each field
	    achievement.setTournamentName(updatedDetails.getTournamentName());
	    achievement.setAchievementRank(updatedDetails.getAchievementRank());
	    achievement.setDateAchived(updatedDetails.getDateAchived());
	    achievement.setTrophie(updatedDetails.getTrophie());

	    // Assuming there's a 'team' relationship that you might need to handle separately
	    // If the 'team' is also supposed to be updated, you'd typically fetch the team entity
	    // and then set it. For now, I'll assume the team remains unchanged or is handled elsewhere.
	 
	    return achievementTeamRepository.save(achievement);
	}
	@Override
	public void deleteAchivementsTeam(AchivementsTeam achivement) {
		achievementTeamRepository.delete(achivement);
		
	}
	
	@Override
	@Transactional // Ensure this method is executed within a transactional context
	public void deleteAchivementsTeamById(Long idAchivement) {
	    // Find the AchivementsTeam by id
	    AchivementsTeam achivement = achievementTeamRepository.findById(idAchivement)
	        .orElseThrow(() -> new RuntimeException("AchivementTeam not found for this id :: " + idAchivement));

	    // Disassociate the AchivementsTeam from the Team
	    Team team = achivement.getTeam();
	    if (team != null) {
	        team.setAchivementsTeam(null); // Assuming the Team class has a method to unset the AchivementsTeam
	        // Optionally save the Team if your JPA context does not auto-detect changes and auto-save
	        // This step might be unnecessary depending on your JPA provider configuration
	    }

	    // Now delete the AchivementsTeam
	    achievementTeamRepository.delete(achivement);
	}


	@Override
	public AchivementsTeam getAchivementsTeam(Long idachivement) {
		
		return achievementTeamRepository.findById(idachivement).get();
	}

	@Override
	public List<AchivementsTeam> getAllAchivementsTeams() {
		
		return achievementTeamRepository.findAll();
	}

}