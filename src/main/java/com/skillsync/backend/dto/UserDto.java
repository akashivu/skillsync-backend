package com.skillsync.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private Set<String> skills;
}
