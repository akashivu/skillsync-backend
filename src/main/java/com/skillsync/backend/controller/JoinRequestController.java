package com.skillsync.backend.controller;

import com.skillsync.backend.model.JoinRequest;
import com.skillsync.backend.service.JoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class JoinRequestController {
    @Autowired
    private JoinRequestService joinRequestService;
    @PostMapping("/request")
    public ResponseEntity<String> sendRequest( @RequestParam Long teamId){
        joinRequestService.sendRequest(teamId);
        return ResponseEntity.ok("request sent");
    }
    @GetMapping("team/{teamId}/getRequest")
    public ResponseEntity<List<JoinRequest>> getAllRequest(@PathVariable Long teamId){
        return ResponseEntity.ok(joinRequestService.getAllRequest(teamId));
    }
    @PostMapping("/request/{id}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable Long id){
        joinRequestService.acceptRequest(id);
        return ResponseEntity.ok("request accepted");
    }
    @PostMapping("/request/{id}/reject")
    public ResponseEntity<String> rejectRequest(@PathVariable Long id) {
        joinRequestService.rejectRequest(id);
        return ResponseEntity.ok("Request rejected");
    }
}
