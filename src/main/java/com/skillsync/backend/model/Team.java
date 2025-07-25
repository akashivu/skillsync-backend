package com.skillsync.backend.model;

import jakarta.persistence.*;

import lombok.Data;


import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name="teams")

public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String projectGoal;
    private String location;
    @ManyToMany
    @JoinTable(
    name="team members",
    joinColumns = @JoinColumn(name="team_id"),
    inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> members = new HashSet<>();

    @ManyToMany
    @JoinTable(
    name="team_skills",
    joinColumns = @JoinColumn(name="team_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills= new HashSet<>();

}
