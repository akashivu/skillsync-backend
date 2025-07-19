package com.skillsync.backend.controller;

import com.skillsync.backend.repository.SkillRepository;
import com.skillsync.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.skillsync.backend.model.User;
import com.skillsync.backend.model.Skill;
import com.skillsync.backend.dto.SkillRequest;
import com.skillsync.backend.service.JWTService;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepo;
    @Autowired private SkillRepository skillRepo;
    @Autowired
    private JWTService jwtService;

    @PostMapping("/skills")
    public ResponseEntity<?> addSkills(@RequestBody SkillRequest request, @RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.substring(7)); // assuming Bearer token
        User user = userRepo.findByEmail(email).orElseThrow();

        for (String skillName : request.getSkills()) {
            Skill skill = skillRepo.findByName(skillName).orElseGet(() -> {
                Skill newSkill = new Skill();
                newSkill.setName(skillName);
                return skillRepo.save(newSkill);
            });
            user.getSkills().add(skill);
        }

        userRepo.save(user);
        return ResponseEntity.ok("Skills added");
    }
}
