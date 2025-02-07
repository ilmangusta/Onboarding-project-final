package com.pollsystem.simpleproject.controller;

import com.pollsystem.simpleproject.domain.ApiResponse;
import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.mapper.PollMapper;
import com.pollsystem.simpleproject.model.PollDTO;
import com.pollsystem.simpleproject.model.PollListPage;
import com.pollsystem.simpleproject.services.OptionService;
import com.pollsystem.simpleproject.services.PollService;
import com.pollsystem.simpleproject.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RestController
public class PollController {

    @Autowired
    private PollService pollService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PollMapper pollMapper;

    ///  ------------ polls api ----------------------------


    @GetMapping(value = "/pollsV2")
    public ResponseEntity<List<PollDTO>> getListPoll(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("PollController - Username: " + username);
        return new ResponseEntity<List<PollDTO>>(pollService.GetListPollDTO(), HttpStatus.OK);
    }

    @PostMapping(value = "/polls")
    public ResponseEntity<?> addPoll(
            @RequestBody PollDTO pollDTO,
            HttpServletRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("PollController - Username: " + username);
        //System.out.println("PollDTO: " + pollDTO);
        Users user = userService.GetUserbyUsername(username);
        return pollService.AddPoll(pollDTO,user,username);
    }

    @GetMapping(value = "/polls")
    public PollListPage getPolls(
            @RequestParam String search,
            @RequestParam int page,
            @RequestParam int size) {
        return pollService.getPollsPage(search,page, size);
    }

    @GetMapping(value = "/polls/{id}")
    public ResponseEntity<?> getPoll(
            @PathVariable(value = "id") Long id){
        return pollService.GetPoll(id);
    }

    @DeleteMapping(value = "/polls/{id}")
    public ResponseEntity<?> deletePoll(
            @PathVariable(value = "id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        //System.out.println("PollController - Username: " + username);
        Users user = userService.GetUserbyUsername(username);
        return pollService.DeletePoll(id,user);
    }


    @PutMapping(value = "/polls/{id}")
    public ResponseEntity<?> updatePoll(
            @PathVariable(value = "id") Long id,
            @RequestBody PollDTO pollDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth_username = authentication.getName();
        //System.out.println("PollController - Username: " + auth_username);
        Users user = userService.GetUserbyUsername(auth_username);
        Poll poll = pollService.GetPollById(id);
        return pollService.UpdatePoll(pollDTO,poll,user);
    }

    @GetMapping(value = "/polls-details/{id}")
    public ResponseEntity<?> updatePoll(
            @PathVariable(value = "id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth_username = authentication.getName();
        //System.out.println("PollController - Username: " + auth_username);
        Users user = userService.GetUserbyUsername(auth_username);
        Poll poll = pollService.GetPollById(id);
        return pollService.GetPollDetails(poll,user);
    }

    @GetMapping(value = "/polls/{id}/vote-TODO")
    public ResponseEntity<?> getVotedPoll(
            @PathVariable(value = "id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth_username = authentication.getName();
        //System.out.println("PollController - Username: " + auth_username);
        Users user = userService.GetUserbyUsername(auth_username);
        Poll poll = pollService.GetPollById(id);
        return pollService.GetPollDetails(poll,user);
    }

}
