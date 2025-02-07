package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Option;
import com.pollsystem.simpleproject.domain.Poll;
import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.repositories.OptionRepository;
import com.pollsystem.simpleproject.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OptionServiceImpl implements OptionService{

    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    protected PollRepository pollRepository;

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
        for (Option option: optionRepository.findAll()){
            if (option.getId().equals(Id)){
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
    public ResponseEntity<?> AddOption(Poll poll, Option option, Users auth_user){

        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        else if(auth_user.getUsername().equals(poll.getOwner())){
            Option optionsave = new Option(option.getMessage(),option.getCreatedAt());
            poll.getOptions().add(optionsave);
            optionsave.setPoll(poll);
            pollRepository.save(poll);
            optionRepository.save(optionsave);
            System.out.println("Option creata con successo");
            return ResponseEntity.status(HttpStatus.CREATED).body(optionsave);
        }else {
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }
    }

    @Override
    public ResponseEntity<?> GetOptionPoll(Poll poll, Long optionId){
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }else{
            for (Option option: poll.getOptions()){
                if (option.getId().equals(optionId)){
                    System.out.println("Option trovata");
                    return ResponseEntity.status(HttpStatus.CREATED).body(option);
                }
            }
            System.out.println("Option non trovata");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Option non trovata");
        }
    }


    @Override
    public ResponseEntity<?> UpdateOptionPoll(Poll poll, Option input_option, Long optionId, Users auth_user){
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        if(auth_user.getUsername().equals(poll.getOwner())){
            for (Option option: poll.getOptions()){
                if (option.getId().equals(optionId)){
                    if (option.getVotes().isEmpty()){
                        //posso modificare la option
                        option.setMessage(input_option.getMessage());
                        option.setCreatedAt(input_option.getCreatedAt());
                        optionRepository.save(option);
                        System.out.println("Option aggiornata con successo");
                        return ResponseEntity.status(HttpStatus.CREATED).body(option);
                    }else{
                        System.out.println("Option gia votata");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Option gia votata");
                    }
                }
            }
            System.out.println("Option non trovata per il poll selezionato");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Option non trovata per il poll selezionato");
        }else {
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }
    }



    @Override
    public ResponseEntity<?> DeleteOptionPoll(Poll poll, Long optionId, Users auth_user){
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        if(auth_user.getUsername().equals(poll.getOwner())){
            for (Option option: poll.getOptions()){
                if (option.getId().equals(optionId)){
                    if (option.getVotes().isEmpty()){
                        //posso modificare la option
                        optionRepository.deleteById(optionId);
                        System.out.println("Option eliminata con successo");
                        return ResponseEntity.status(HttpStatus.CREATED).body("Option cancellata");
                    }else{
                        System.out.println("Option gia votata - Impossibile eliminare");
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Option gia votata - Impossibile eliminare");
                    }
                }
            }
            System.out.println("Option non trovata per il poll selezionato");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Option non trovata per il poll selezionato");
        }else {
            System.out.println("Accesso non autorizzato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
        }
    }


    @Override
    public ResponseEntity<?> VoteOptionPoll(Poll poll, Long optionId, Users auth_user){


        ///  da fare
        if (poll == null){
            System.out.println("Poll non esiste");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poll non esiste");
        }
        if(auth_user.getUsername().equals(poll.getOwner())) {
            //proprietario non puo votare
            System.out.println("Proprietario non puo votare");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Proprietario non può votare");
        }else {
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

        }
    }


}
