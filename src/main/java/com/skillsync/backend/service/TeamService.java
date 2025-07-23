package com.skillsync.backend.service;

import com.skillsync.backend.model.Team;
import com.skillsync.backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    public List<Team> searchTeam(String skill, String location){
        return teamRepository.findAll().stream().filter(team->{
            boolean skillmatches=(skill==null||skill.isEmpty()||team.getSkills().stream().anyMatch(s->s.getName().equalsIgnoreCase(skill)));
            boolean locationMatches=(location == null || location.isEmpty()) ||
                    team.getLocation().equalsIgnoreCase(location);
            return skillmatches && locationMatches;
        })
        .collect(Collectors.toList());
    }
}
