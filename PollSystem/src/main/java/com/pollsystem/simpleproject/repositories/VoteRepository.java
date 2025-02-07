package com.pollsystem.simpleproject.repositories;

import com.pollsystem.simpleproject.domain.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Long> {
}
