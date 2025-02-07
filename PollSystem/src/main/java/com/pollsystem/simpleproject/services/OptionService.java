package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.Users;
import org.springframework.http.ResponseEntity;

public interface OptionService {

    Iterable<Option> findAll();

    void save(Option option);

    Option findById(Long Id);

    void deleteById(Long id);

    ResponseEntity<?> AddOption(Poll poll, Option option, Users auth_user);

    ResponseEntity<?> GetOptionPoll(Poll poll, Long optionId);

    ResponseEntity<?> UpdateOptionPoll(Poll poll, Option input_option, Long optionId, Users auth_user);

    ResponseEntity<?> DeleteOptionPoll(Poll poll, Long optionId, Users auth_user);

    ResponseEntity<?> VoteOptionPoll(Poll poll, Long optionId, Users auth_user);
}
