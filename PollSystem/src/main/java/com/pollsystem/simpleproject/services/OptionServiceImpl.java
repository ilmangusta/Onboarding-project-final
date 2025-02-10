package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.domain.Vote;
import com.pollsystem.simpleproject.repositories.OptionRepository;
import com.pollsystem.simpleproject.repositories.PollRepository;
import com.pollsystem.simpleproject.repositories.UsersRepository;
import com.pollsystem.simpleproject.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Iterable<Option> findAll() {
        return optionRepository.findAll();
    }

    @Override
    public void save(Option option) {
        optionRepository.save(option);
    }

    @Override
    public Option findById(Long Id) {
        for (Option option : optionRepository.findAll()) {
            if (option.getId().equals(Id)) {
                return option;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        optionRepository.deleteById(id);
    }


    @Override
    public ResponseEntity<?> AddOption(Poll poll, Option option, Users auth_user) {
        if (poll == null) {
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        if (poll.getStatus().equals("EXPIRED")) {
            System.out.println("Poll scaduto");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Poll scaduto");
        } else if (auth_user.getUsername().equals(poll.getOwner())) {
            Option optionsave = new Option(option.getMessage(), option.getCreatedAt());
            poll.getOptions().add(optionsave);
            optionsave.setPoll(poll);
            pollRepository.save(poll);
            optionRepository.save(optionsave);
            System.out.println("Option creata con successo");
            return ResponseEntity.status(HttpStatus.CREATED).body(optionsave);
        } else {
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }
    }

    @Override
    public ResponseEntity<?> GetOptionPoll(Poll poll, Long optionId) {
        if (poll == null) {
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        } else {
            for (Option option : poll.getOptions()) {
                if (option.getId().equals(optionId)) {
                    System.out.println("Option trovata");
                    return ResponseEntity.status(HttpStatus.CREATED).body(option);
                }
            }
            System.out.println("Option non trovata");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Option non trovata");
        }
    }


    @Override
    public ResponseEntity<?> UpdateOptionPoll(Poll poll, Option input_option, Long optionId, Users auth_user) {
        if (poll == null) {
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        if (poll.getStatus().equals("EXPIRED")) {
            System.out.println("Poll scaduto");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Poll scaduto");
        }
        if (auth_user.getUsername().equals(poll.getOwner())) {
            for (Option option : poll.getOptions()) {
                if (option.getId().equals(optionId)) {
                    if (option.getVotes().isEmpty()) {
                        //posso modificare la option
                        option.setMessage(input_option.getMessage());
                        option.setCreatedAt(input_option.getCreatedAt());
                        optionRepository.save(option);
                        System.out.println("Option aggiornata con successo");
                        return ResponseEntity.status(HttpStatus.CREATED).body(option);
                    } else {
                        System.out.println("Option gia votata");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Option gia votata");
                    }
                }
            }
            System.out.println("Option non trovata per il poll selezionato");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Option non trovata per il poll selezionato");
        } else {
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }
    }


    @Override
    public ResponseEntity<?> DeleteOptionPoll(Poll poll, Long optionId, Users auth_user) {
        if (poll == null) {
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        if (poll.getStatus().equals("EXPIRED")) {
            System.out.println("Poll scaduto");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Poll scaduto");
        }
        if (auth_user.getUsername().equals(poll.getOwner())) {
            for (Option option : poll.getOptions()) {
                if (option.getId().equals(optionId)) {
                    if (option.getVotes().isEmpty()) {
                        //posso modificare la option
                        optionRepository.deleteById(optionId);
                        System.out.println("Option eliminata con successo");
                        return ResponseEntity.status(HttpStatus.CREATED).body("Option cancellata");
                    } else {
                        System.out.println("Option gia votata - Impossibile eliminare");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Option gia votata - Impossibile eliminare");
                    }
                }
            }
            System.out.println("Option non trovata per il poll selezionato");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Option non trovata per il poll selezionato");
        } else {
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }
    }


    @Override
    public ResponseEntity<?> VoteOptionPoll(Poll poll, Long optionId, Users auth_user) {
        ///  da fare
        if (poll == null) {
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        if (poll.getStatus().equals("EXPIRED")) {
            System.out.println("Poll scaduto");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Poll scaduto");
        }
        if (auth_user.getUsername().equals(poll.getOwner())) {
            //proprietario non puo votare
            System.out.println("Proprietario non puo votare");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        } else if (this.findById(optionId) == null) {
            System.out.println("Option non trovata per il poll selezionato");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Option non trovata per il poll selezionato");
        } else {
            //option esiste e va controllato se utente ha gia votato o meno
            Vote new_vote = new Vote(new Date(System.currentTimeMillis()));
            for (Vote votes : auth_user.getVote()) {
                if (poll.getOptions().contains(voteRepository.getReferenceById(votes.getId()).getOption())) {
                    //un voto dell utente appartiene al sondagggio per cui sta votando, aggiorno il voto
                    System.out.println("Utente ha gia votato una opzione di questo poll....aggiorno il voto");
                    Option option = this.findById(optionId);
                    option.getVotes().add(votes);
                    votes.setOption(option);
                    votes.setUser(auth_user);
                    votes.setVotedAt(new Date(System.currentTimeMillis()));
                    auth_user.getVote().add(votes);
                    optionRepository.save(option);
                    voteRepository.save(votes);
                    usersRepository.save(auth_user);
                    System.out.println("Option aggiornata con successo");
                    System.out.println(auth_user.getVote());
                    System.out.println(option.getVotes());
                    //devo eliminare il voto dall altra option
                    return ResponseEntity.status(HttpStatus.CREATED).body(option);
                }
            }
            //user non ha mai votato nessuna option nel sondaggio, ok nuovo voto
            Option option = this.findById(optionId);
            option.getVotes().add(new_vote);
            new_vote.setOption(option);
            new_vote.setUser(auth_user);
            auth_user.getVote().add(new_vote);
            optionRepository.save(option);
            voteRepository.save(new_vote);
            usersRepository.save(auth_user);
            System.out.println("Option aggiornata con successo - nuovo voto");
            System.out.println(auth_user.getVote());
            System.out.println(option.getVotes());
            return ResponseEntity.status(HttpStatus.CREATED).body(option);
        }
    }

}
