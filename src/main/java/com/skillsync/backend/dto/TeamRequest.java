package com.skillsync.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class TeamRequest {
    private String name;
    private String projectGoal;
    private List<String> membersEmail;
}
