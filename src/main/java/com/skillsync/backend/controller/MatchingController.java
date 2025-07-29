package com.skillsync.backend.controller;

import com.skillsync.backend.dto.UserDto;
import com.skillsync.backend.model.Skill;
import com.skillsync.backend.model.User;
import com.skillsync.backend.repository.UserRepository;
import com.skillsync.backend.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/match")
public class MatchingController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/suggestions")
    public ResponseEntity<List<UserDto>> getMatchingUsers(@RequestHeader("Authorization") String token) {

        String email = jwtService.extractUsername(token.substring(7));
        User currentUser = userRepo.findByEmail(email).orElseThrow();


        Set<String> currentUserSkills = currentUser.getSkills().stream()
                .map(Skill::getName)
                .collect(Collectors.toSet());


        List<User> allUsers = userRepo.findAll();

        List<UserDto> matchedUsers = allUsers.stream()
                .filter(user -> !user.getEmail().equals(email)) // Exclude current user
                .filter(user -> {
                    Set<String> otherSkills = user.getSkills().stream()
                            .map(Skill::getName)
                            .collect(Collectors.toSet());
                    otherSkills.retainAll(currentUserSkills); // Keep only common skills
                    return !otherSkills.isEmpty();
                })
                .map(user -> {
                    Set<String> skillNames = user.getSkills().stream()
                            .map(Skill::getName)
                            .collect(Collectors.toSet());
                    return new UserDto(user.getUsername(), user.getEmail(), skillNames);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(matchedUsers);
    }
    }

