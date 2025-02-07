package com.pollsystem.simpleproject.repositories;

import com.pollsystem.simpleproject.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Long> {

    @Query("SELECT p FROM Poll p WHERE p.expiresAt = CURRENT_DATE AND p.status != 'EXPIRED'")
    List<Poll> findExpiringPollsToday();
}
