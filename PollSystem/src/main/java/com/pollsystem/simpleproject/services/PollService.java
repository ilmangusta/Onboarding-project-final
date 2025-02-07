package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.model.PollDTO;
import com.pollsystem.simpleproject.model.PollListPage;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PollService {
    Iterable<Poll> findAll();

    boolean save(Poll poll);

    List<PollDTO> GetListPollDTO();

    PollDTO GetPollDTOById(Long id);

    Poll GetPollById(Long id);

    void DeletePoll(Long id);

    ResponseEntity<?> UpdatePoll(PollDTO input, Poll poll, Users user);

    ResponseEntity<?> DeletePoll(Long id, Users user);

    ResponseEntity<?> GetPoll(Long id);

    ResponseEntity<?> AddPoll(PollDTO pollDTO, Users user, String Auth_user);

    ResponseEntity<?> GetPollDetails(Poll poll, Users user);

    Long GetWinningOption(Long id);

    int GetPercentWinner(Long id);

    //Per gestire la paginazione
    PollListPage getPollsPage(String search,int page, int size);
}
