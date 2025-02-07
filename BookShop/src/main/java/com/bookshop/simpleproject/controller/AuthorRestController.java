package com.bookshop.simpleproject.controller;


import com.bookshop.simpleproject.domain.Author;
import com.bookshop.simpleproject.mapper.AuthorMapper;
import com.bookshop.simpleproject.model.AuthorDTO;
import com.bookshop.simpleproject.model.BookDTO;
import com.bookshop.simpleproject.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;

    @GetMapping(value = "/GetAuthors")
    public ResponseEntity<List<AuthorDTO>> getAuthors(Model model){
        return new ResponseEntity<List<AuthorDTO>>(authorService.GetListAuthorsDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/GetAuthors/{AuthorId}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable("AuthorId") Long id){
        if (authorService.findById(id) != null){
            return new ResponseEntity<AuthorDTO>(authorService.GetAuthorDTO(id), HttpStatus.OK);
        }else {
            throw new NoSuchElementException();
        }
    }

    @DeleteMapping(value = "/DeleteAuthors/{AuthorId}")
    public void deleteAuthor(@PathVariable("AuthorId") Long id){
        if (authorService.findById(id) != null){
            authorService.deleteById(id);
        }else {
            throw new NoSuchElementException();
        }
    }

    @PostMapping(value = "/AddAuthor")
    public ResponseEntity<Author> addAuthorBody(
            @RequestBody AuthorDTO authorDTO){

        Author author = new Author(authorDTO.getFirstName(),authorDTO.getLastName());
        authorService.save(author);
        return new ResponseEntity<Author>(author, HttpStatus.OK);
    }

    @PostMapping(value = "/EditAuthors/{AuthorId}")
    public ResponseEntity<Author> editAuthor(
            @PathVariable("AuthorId") Long id,
            @RequestBody AuthorDTO authorDTO){

        if (authorService.findById(id) != null){
            Author author = authorService.findById(id);
            author.setFirstName(authorDTO.getFirstName());
            author.setLastName(authorDTO.getLastName());
            authorService.save(author);
            return new ResponseEntity<Author>(author, HttpStatus.OK);
        }else {
            throw new NoSuchElementException();
        }
    }

}
