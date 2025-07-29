package com.skillsync.backend.service;


import com.skillsync.backend.model.JoinRequest;
import com.skillsync.backend.model.Team;
import com.skillsync.backend.model.User;
import com.skillsync.backend.repository.JoinRequsetRepository;
import com.skillsync.backend.repository.TeamRepository;
import com.skillsync.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JoinRequestService {
     @Autowired
    private TeamRepository teamRepository;
     @Autowired
    private UserRepository userRepository;
     @Autowired
    private JoinRequsetRepository joinRequsetRepository;
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));
    }

    public void sendRequest(Long teamId){
   User user = getCurrentUser();
   Team team = teamRepository.findById(teamId).orElseThrow();
        if (joinRequsetRepository.existsByRequesterAndTeamAndStatus(user, team, "PENDING")) {
            throw new RuntimeException("Already requested!");
        }
        JoinRequest request = new JoinRequest();
        request.setRequester(user);
        request.setTeam(team);
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());

        joinRequsetRepository.save(request);
    }
    public List<JoinRequest> getAllRequest(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        return joinRequsetRepository.findByTeamAndStatus(team, "PENDING");
    }
    public void acceptRequest(Long requestId) {
        JoinRequest request = joinRequsetRepository.findById(requestId).orElseThrow();
        Team team = request.getTeam();
        User user = request.getRequester();


        team.getMembers().add(user);
        request.setStatus("ACCEPTED");

        teamRepository.save(team);
        joinRequsetRepository.save(request);
    }
    public void rejectRequest(Long requestId) {
        JoinRequest request = joinRequsetRepository.findById(requestId).orElseThrow();
        request.setStatus("REJECTED");
        joinRequsetRepository.save(request);
    }
}
