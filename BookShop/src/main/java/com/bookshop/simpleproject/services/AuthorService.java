package com.bookshop.simpleproject.services;

import com.bookshop.simpleproject.domain.Author;
import com.bookshop.simpleproject.model.AuthorDTO;

import java.util.List;

public interface AuthorService {
    Iterable<Author> findAll();
    void save(Author author);

    Author findById(Long Id);

    void deleteById(Long id);

    List<AuthorDTO> GetListAuthorsDTO();

    AuthorDTO GetAuthorDTO(Long id) throws IllegalArgumentException;
}
