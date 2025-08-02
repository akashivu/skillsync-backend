package com.skillsync.backend.repository;

import com.skillsync.backend.model.Message;
import com.skillsync.backend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTeamOrderByTimestampAsc(Team team);

}
