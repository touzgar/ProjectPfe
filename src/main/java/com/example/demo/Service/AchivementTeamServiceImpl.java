package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.Model.AchivementTeam;
import com.example.demo.Model.Team;
import com.example.demo.Repository.AchivementTeamRepository;
import com.example.demo.Repository.TeamRepository;

@Service
public class AchivementTeamServiceImpl implements AchivementTeamService {
    @Autowired
    AchivementTeamRepository achivementTeamRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public AchivementTeam saveAchievementWithTeam(AchivementTeam achivementTeam, String teamName) {
        Team team = teamRepository.findByTeamName(teamName)
                .orElseGet(() -> {
                    Team newTeam = new Team();
                    newTeam.setTeamName(teamName);
                    return teamRepository.save(newTeam);
                });
        achivementTeam.setTeam(team);
        return achivementTeamRepository.save(achivementTeam);
    }

    @Override
    public AchivementTeam saveAchivementTeam(AchivementTeam achivementTeam) {
        return achivementTeamRepository.save(achivementTeam);
    }

    @Override
    @Transactional
    public AchivementTeam UpdateAchivementTeam(AchivementTeam achivementTeam) {
        return achivementTeamRepository.save(achivementTeam);
    }

    @Override
    public void deleteAchivementTeam(AchivementTeam achivementTeam) {
        achivementTeamRepository.delete(achivementTeam);
    }

    @Override
    public void deleteAchivementTeamById(Long idAchivementTeam) {
        achivementTeamRepository.deleteById(idAchivementTeam);
    }

    @Override
    public AchivementTeam getAchivementTeam(Long idAchivementTeam) {
        return achivementTeamRepository.findById(idAchivementTeam).orElse(null);
    }

    @Override
    public List<AchivementTeam> getAllAchivementTeams() {
        return achivementTeamRepository.findAll();
    }
}
