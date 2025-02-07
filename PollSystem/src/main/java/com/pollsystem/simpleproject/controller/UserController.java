package com.pollsystem.simpleproject.controller;

import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.model.*;
import com.pollsystem.simpleproject.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${app.config.expirationTime}")
    private int EXPIRATION_TIME;

    ///---------------- authentication api ----------------------

    @GetMapping(value = "/GetUsers")
    public ResponseEntity<?> getUsers(HttpServletRequest request){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String auth_username = authentication.getName();
            //System.out.println("UserController - Username: " + auth_username);
            return ResponseEntity.status(HttpStatus.OK).body(userService.GetListUserDTO());
        }catch(ExpiredJwtException e){
            System.out.println("UserController - Login non ok - JWT Scaduto - ERRORE E: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login non ok - JWT Scaduto - ERRORE E: "+ e.getMessage());
        }catch (Exception e){
            System.out.println("UserController - Login non ok - Username non trovato - ERRORE E: "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login non ok - User not found - ERRORE E:"+ e.getMessage());
        }
    }

    @PostMapping(value = "/Registration")
    public ResponseEntity<?>  Registration(
            @RequestBody RegisterModelDTO register) {
        Users user = new Users(register.getUsername(), register.getPassword(), register.getEmail());
        if (userService.register(user)) {
            System.out.println("Utente creato con successo");
            return ResponseEntity.status(HttpStatus.CREATED).body("Utente creato con successo");
        } else {
            ValidationErrorResponse errorResponse = new ValidationErrorResponse(new Date(System.currentTimeMillis()),0,"Impossibile creare l'utente", register.getUsername());
            System.out.println("Impossibile creare l'utente");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping(value = "/Login")
    public ResponseEntity<?> Login(
            @RequestBody LoginModelDTO loginmodel){
        String username = loginmodel.getUsername();
        String password = loginmodel.getPassword();
        if (userService.checkPasswordUser(username,password)){
            Users user = userService.GetUserbyUsername(username);
            String token = userService.generateToken(user);
            user.setToken(token);
            userService.save(user);
            System.out.println("Login eseguito con successo - JWT: " + token);
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token,new Date(System.currentTimeMillis()+ (1000 * 60) * EXPIRATION_TIME)));
        }else {
            System.out.println("Login Fallito - Username errato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
        }
    }
}



