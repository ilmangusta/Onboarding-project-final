package com.pollsystem.simpleproject.repositories;

import com.pollsystem.simpleproject.domain.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Long> {

    @Query("SELECT p FROM Poll p WHERE p.expiresAt <= CURRENT_DATE AND p.status != 'EXPIRED'")
    List<Poll> findExpiringPollsToday();

    //supporto per la paginazione
    @Query("SELECT p FROM Poll p WHERE p.question LIKE %:search%") //supporto per la query col parametro search
    Page<Poll> findAll(@Param("search") String search, Pageable pageable);
}
