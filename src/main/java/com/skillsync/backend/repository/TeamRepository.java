package com.skillsync.backend.repository;

import com.skillsync.backend.model.Team;
import com.skillsync.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
  List<Team> findByMembersContaining(User user);
}
