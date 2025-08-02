package com.skillsync.backend.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private Long teamId;
    private String content;
}
