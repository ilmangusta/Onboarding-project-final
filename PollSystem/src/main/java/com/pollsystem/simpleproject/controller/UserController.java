package com.pollsystem.simpleproject.controller;

import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.model.*;
import com.pollsystem.simpleproject.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    ///---------------- authentication api ----------------------

    @GetMapping(value = "/GetUsers")
    public ResponseEntity<List<UsersDTO>> getUsers(HttpServletRequest request){

        //System.out.println(request.toString());
        try {
            System.out.println("UserController - Username: " + request.getRemoteUser());
            String username = request.getRemoteUser();
            Users user = userService.GetUserbyUsername(username);
            //boolean validToken = userService.validtoken(user);
            //if (userService.validtoken(user)){
                //System.out.println("Login ok!");
                return new ResponseEntity<List<UsersDTO>>(userService.GetListUserDTO(), HttpStatus.OK);
            //}else{
            //    System.out.println("Login non ok!");
            //    return new ResponseEntity<List<UsersDTO>>(HttpStatus.BAD_REQUEST);
            //}
        }catch(ExpiredJwtException e){
            System.out.println("UserController - Login non ok - JWT Scaduto - ERRORE E: "+ e.getMessage());
            return new ResponseEntity<List<UsersDTO>>(HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            System.out.println("UserController - Login non ok - Username non trovato - ERRORE E: "+ e.getMessage());
            return new ResponseEntity<List<UsersDTO>>(HttpStatus.NOT_FOUND);
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

        int res = userService.checkPasswordUser(username,password);
        //System.out.println(userService.verify(user));
        if (res == 1){
            Users user = userService.GetUserbyUsername(username);
            String token = userService.generateToken(user);
            System.out.println(token);
            user.setToken(token);
            userService.save(user);
            System.out.println("Login eseguito con successo - JWT: " + token);
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token,new Date(System.currentTimeMillis())));
        }else if (res == -1) {
            System.out.println("Login Fallito - Username errato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
        }else {
            System.out.println("Login Fallito - Username errato");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
        }
    }

    //@PostMapping(value = "/Login2")
    //public String login(
    //        @RequestBody LoginModelDTO loginmodel){
    //    return userService.verifyCredential(loginmodel);
    //}
}



