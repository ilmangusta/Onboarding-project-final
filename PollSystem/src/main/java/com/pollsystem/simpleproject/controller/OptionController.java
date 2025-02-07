package com.pollsystem.simpleproject.controller;

import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.services.OptionService;
import com.pollsystem.simpleproject.services.PollService;
import com.pollsystem.simpleproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
public class OptionController {

    @Autowired
    private OptionService optionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PollService pollService;

    ///---------------- option api ----------------------

    @PostMapping(value = "/polls/{id}/option")
    public ResponseEntity<?> addOption(
            @PathVariable(value = "id") Long id,
            @RequestBody Option option){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("OptionController - Authenticated Username: " + username);

        Users user = userService.GetUserbyUsername(username);
        Poll poll = pollService.GetPollById(id);
        return optionService.AddOption(poll, option,user);
    }

    @GetMapping(value = "/polls/{id}/option/{optionId}")
    public ResponseEntity<?> getOptionPoll(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "optionId") Long optionId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("OptionController - Authenticated Username: " + username);

        Poll poll = pollService.GetPollById(id);
        return optionService.GetOptionPoll(poll,optionId);
    }


    @DeleteMapping(value = "/polls/{id}/option/{optionId}")
    public ResponseEntity<?> deleteOptionPoll(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "optionId") Long optionId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("OptionController - Authenticated Username: " + username);

        Users user = userService.GetUserbyUsername(username);
        Poll poll = pollService.GetPollById(id);
        return optionService.DeleteOptionPoll(poll,optionId, user);
    }


    @PutMapping(value = "/polls/{id}/option/{optionId}")
    public ResponseEntity<?> updateOptionPoll(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "optionId") Long optionId,
            @RequestBody Option option){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("OptionController - Authenticated Username: " + username);

        Users user = userService.GetUserbyUsername(username);
        Poll poll = pollService.GetPollById(id);
        return optionService.UpdateOptionPoll(poll,option,optionId, user);
    }


    @PutMapping(value = "/polls/{id}/option/{optionId}/vote")
    public ResponseEntity<?> voteOption(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "optionId") Long optionId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("OptionController - Authenticated Username: " + username);

        Users user = userService.GetUserbyUsername(username);
        Poll poll = pollService.GetPollById(id);
        return optionService.VoteOptionPoll(poll,optionId,user);
    }






}
