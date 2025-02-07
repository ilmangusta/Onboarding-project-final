package com.pollsystem.simpleproject.bootstrap;

import com.pollsystem.simpleproject.domain.*;
import com.pollsystem.simpleproject.repositories.*;
import com.pollsystem.simpleproject.domain.*;
import com.pollsystem.simpleproject.repositories.*;
import com.pollsystem.simpleproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BootStrapData implements CommandLineRunner {

    private final PollRepository pollRepository;
    private final UsersRepository usersRepository;

    @Autowired
    private UserService userService;

    public BootStrapData(PollRepository pollRepository, UsersRepository usersRepository) {
        this.pollRepository = pollRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //loadUser();
    }

    public void loadUser(){
        //String dataStr = "2024-02-03";
        //LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //System.out.println(data); // Output: 2024-02-03

        //Users user2 = userService.GetUserbyUsername("mangusta");
        //usersRepository.save(new Users("utente","provapass","provamail"));

        pollRepository.save(new Poll( "Juve o milan3?","mangusta",Date.valueOf("2025-02-04T21:19:19.553Z"),"Attivo"));
        System.out.println("primo messo");
        pollRepository.save(new Poll( "Juve o milan7?","mangusta4",Date.valueOf("2025-02-04T21:19:19.553Z"),"Attivo"));
        System.out.println("2 messo");
        pollRepository.save(new Poll( "Juve o milan8?","mangusta4",Date.valueOf(LocalDate.parse("25/10/2026", DateTimeFormatter.ofPattern("dd/MM/yyyy"))) ,"Attivo"));
        System.out.println("3 messo");

        System.out.println("Utente caricato");
    }

}
