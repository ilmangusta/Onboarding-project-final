package com.pollsystem.simpleproject.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "/errors/notfound";  // Reindirizza alla pagina di errore
    }

    //@ExceptionHandler(Exception.class)
    //public String Exceptionhandler(Exception e) {
    //    //model.addAttribute("error", e.getMessage());
    //    return "SomeErroroccurred";  // Reindirizza alla pagina di errore
    //}

    // non trovato → 404
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<String> handlePollNotFound(ChangeSetPersister.NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Accesso non autorizzato → 401
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Accesso non autorizzato");
    }

    // Token scaduto → 401
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpireToken(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token Scaduto - Accesso non autorizzato");
    }

    //Eccezioni generiche → 500
   @ExceptionHandler(Exception.class)
   public ResponseEntity<String> handleGenericException(Exception ex) {
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server");
   }


}