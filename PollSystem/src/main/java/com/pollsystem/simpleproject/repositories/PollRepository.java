package com.pollsystem.simpleproject.repositories;

import com.pollsystem.simpleproject.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}
