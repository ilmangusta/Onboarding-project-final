package com.pollsystem.simpleproject.repositories;

import com.pollsystem.simpleproject.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends JpaRepository<Vote,Long> {
}
