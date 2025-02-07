package com.bookshop.simpleproject.services;

import com.bookshop.simpleproject.domain.Author;
import com.bookshop.simpleproject.domain.Book;
import com.bookshop.simpleproject.mapper.AuthorMapper;
import com.bookshop.simpleproject.model.AuthorDTO;
import com.bookshop.simpleproject.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authRepo;

    @Autowired
    private AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public Iterable<Author> findAll() {
        return authRepo.findAll();
    }

    @Override
    public void save(Author author) {
        authRepo.save(author);
    }

    @Override
    public Author findById(Long id) {
        for (Author author: authRepo.findAll()){
            if (author.getId().equals(id)){
                return author;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Long id)  throws IllegalArgumentException {
        authRepo.deleteById(id);
    }

    @Override
    public List<AuthorDTO> GetListAuthorsDTO() {
        return authRepo
                .findAll()
                .stream()
                .map(book -> {
                    return authorMapper.AuthorToDTO(book);
                })
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO GetAuthorDTO(Long id) throws IllegalArgumentException {
        Book bookPJA;
        for (Author author: authRepo.findAll()){
            if (author.getId().equals(id)){
                return authorMapper.AuthorToDTO(author);
            }
        }
        throw  new IllegalArgumentException();
    }


}
