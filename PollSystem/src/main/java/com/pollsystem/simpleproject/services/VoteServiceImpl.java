package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Vote;
import com.pollsystem.simpleproject.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Iterable<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Override
    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    @Override
    public Vote findById(Long Id) {
        for (Vote vote: voteRepository.findAll()){
            if (vote.getId().equals(Id)){
                return vote;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        voteRepository.deleteById(id);
    }

}
