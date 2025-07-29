package com.skillsync.backend.repository;

import com.skillsync.backend.model.JoinRequest;
import com.skillsync.backend.model.Team;
import com.skillsync.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequsetRepository extends JpaRepository<JoinRequest,Long> {
    List<JoinRequest> findByTeamAndStatus(Team team, String status);
    boolean existByRequesterAndTeamAndStatus(User requseter, Team team, String status);
}
