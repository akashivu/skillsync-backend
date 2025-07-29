package com.skillsync.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User requester;

    @ManyToOne
    private Team team;

    private String status;

    private LocalDateTime createdAt;


}
