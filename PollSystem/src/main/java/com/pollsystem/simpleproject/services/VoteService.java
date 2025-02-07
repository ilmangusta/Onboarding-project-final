package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Vote;

public interface VoteService {

    Iterable<Vote> findAll();

    void save(Vote vote);

    Vote findById(Long Id);

    void deleteById(Long id);
}
