package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.*;
import com.pollsystem.simpleproject.mapper.PollMapper;
import com.pollsystem.simpleproject.model.PollDTO;
import com.pollsystem.simpleproject.model.PollDetails;
import com.pollsystem.simpleproject.repositories.PollRepository;
import com.pollsystem.simpleproject.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private PollMapper pollMapper;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Iterable<Poll> findAll() {
        return pollRepository.findAll();
    }

    @Override
    public boolean save(Poll poll) {
        if (this.GetPollById(poll.getId())==null){
            pollRepository.save(poll);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<PollDTO> GetListPollDTO() {
        return pollRepository
                .findAll()
                .stream()
                .map(
                poll -> {
                    return pollMapper.PollToPollDTO(poll);
                }).collect(Collectors.toList());
    }

    @Override
    public PollDTO GetPollDTOById(Long id) {
        for (Poll poll: pollRepository.findAll()){
            if (Objects.equals(poll.getId(), id)){
                return pollMapper.PollToPollDTO(poll);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Poll GetPollById(Long id) {
        for (Poll poll: pollRepository.findAll()){
            if (poll.getId().equals(id)){
                return poll;
            }
        }
        return null;
    }

    @Override
    public void DeletePoll(Long id) {
        pollRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<?> UpdatePoll(PollDTO input, Poll poll, Users user){
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }else if (!poll.getOwner().equals(user.getUsername())){
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }else {
            for (Option option : poll.getOptions()){
                if (!option.getVotes().isEmpty()){
                    System.out.println("Poll non aggiornabile");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Poll non aggiornabile");
                }
            }
            poll.setStatus(input.getStatus());
            poll.setQuestion(input.getQuestion());
            poll.setExpiresAt(input.getExpiresAt());
            poll.setStatus(input.getStatus());
            pollRepository.save(poll);
            System.out.println("Poll aggiornato");
            return ResponseEntity.status(HttpStatus.CREATED).body(pollMapper.PollToPollDTO(poll));
        }
    }

    @Override
    public ResponseEntity<?> DeletePoll(Long id, Users user) {
        Poll poll = this.GetPollById(id);
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        else if(poll.getOwner().equals(user.getUsername())){
            this.DeletePoll(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("Poll Eliminato");
        }
        else {
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }
    }


    @Override
    public ResponseEntity<?> GetPoll(Long id) {
        Poll poll = this.GetPollById(id);
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }else{
            ApiResponse response = new ApiResponse(HttpStatus.OK,"Poll Trovato");
            return ResponseEntity.status(HttpStatus.OK).body(pollMapper.PollToPollDTO(poll));
        }
    }

    @Override
    public ResponseEntity<?> AddPoll(PollDTO pollDTO, Users user, String Auth_user) {

        if (!Auth_user.equals(pollDTO.getOwner())){
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }else if(this.GetPollById(pollDTO.getId()) != null){
            System.out.println("Poll validation fallita");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Poll validation fallita");
        }else{
            Poll poll = new Poll( pollDTO.getQuestion(), pollDTO.getOwner(), pollDTO.getExpiresAt(), pollDTO.getStatus());
            pollRepository.save(poll);
            return ResponseEntity.status(HttpStatus.OK).body("Poll creato con successo");
        }
    }


    @Override
    public ResponseEntity<?> GetPollDetails(Poll poll, Users user) {
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }else {
            WinnerOption wo = new WinnerOption(poll.getId(),this.GetWinningOption(poll.getId()) , this.GetPercentWinner(poll.getId()));
            PollDetails pollDetails = new PollDetails(
                    poll.getId(),
                    poll.getOwner(),
                    poll.getExpiresAt(),
                    poll.getStatus(),
                    poll.getQuestion(),
                    wo,
                    poll.getOptions());
            System.out.println("Poll trovato");
            return ResponseEntity.status(HttpStatus.OK).body(pollDetails);
        }
    }


    public Long GetWinningOption(Long id){
        int winningOption = 0;
        Long optionId = (long) 0;
        Set<Integer> listOfVote = new HashSet<>();
        for (Option option: this.GetPollById(id).getOptions()){
            if (option.getVotes().size() > winningOption){
                optionId = option.getId();
            }
        }
        System.out.println("Winning option: " + optionId);
        return optionId;
    }

    public int GetPercentWinner(Long id){
        //1 12
        //2 34
        //3 2
        //4 9
        //5 10
        int max = 0;
        for (Option option: this.GetPollById(id).getOptions()){
            System.out.println(option.getVotes().size());
            if (option.getVotes().size() > max){
                max = option.getVotes().size();
            }
        }
        System.out.println("numero totali opzioni poll: " +this.GetPollById(id).getOptions().size());
        return max == 0 ? 0 : (max/(this.GetPollById(id).getOptions().size()));
    }

}
