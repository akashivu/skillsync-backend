package com.skillsync.backend.controller;
import com.skillsync.backend.model.Team;
import com.skillsync.backend.model.User;
import com.skillsync.backend.dto.TeamRequest;
import com.skillsync.backend.repository.TeamRepository;
import com.skillsync.backend.repository.UserRepository;
import com.skillsync.backend.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/team")
public class TeamController {

    @Autowired private TeamRepository teamRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private JWTService jwtService;

    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody TeamRequest req, @RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User creator = userRepo.findByEmail(email).orElseThrow();

        Team team = new Team();
        team.setName(req.getName());
        team.setProjectGoal(req.getProjectGoal());

        // Add creator to team
        team.getMembers().add(creator);

        // Add other invited users
        if (req.getMemberEmails() != null) {
            for (String userEmail : req.getMemberEmails()) {
                userRepo.findByEmail(userEmail).ifPresent(team.getMembers()::add);
            }
        }

        teamRepo.save(team);
        return ResponseEntity.ok("Team created successfully");
    }


}

