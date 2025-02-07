package com.bookshop.simpleproject.services;

import com.bookshop.simpleproject.domain.Book;
import com.bookshop.simpleproject.mapper.BookMapper;
import com.bookshop.simpleproject.model.BookDTO;
import com.bookshop.simpleproject.repositories.BookRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepositoryJPA bookRepositoryJPA;

    @Autowired
    private BookMapper bookMapper;

    public BookServiceImpl( BookRepositoryJPA bookRepositoryJPA) {
        this.bookRepositoryJPA = bookRepositoryJPA;
    }

    @Override
    public Iterable<Book> findAll() {
        return (List<Book>) bookRepositoryJPA.findAll();
    }

    @Override
    public void save(Book book){
        bookRepositoryJPA.save(book);
    }

    @Override
    public Book findById(Long id) throws Exception {
        for (Book book: bookRepositoryJPA.findAll()){
            if (book.getId().equals(id)){
                return book;
            }
        }
        throw  new IllegalArgumentException();
    }

    @Override
    public void deleteById(Long id) {
        bookRepositoryJPA.deleteById(id);
    }

    @Override
    public List<Book> GetListBooks(){
        return new ArrayList<>(bookRepositoryJPA
                .findAll());
    }

    @Override
    public List<BookDTO> GetListBooksDTO(){
        return bookRepositoryJPA
                .findAll()
                .stream()
                .map(book -> {
                    return bookMapper.bookTobookDTO(book);
                })
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO GetBookDTO(Long id) throws IllegalArgumentException {
        for (Book book: bookRepositoryJPA.findAll()){
            if (book.getId().equals(id)){
                return bookMapper.bookTobookDTO(book);
            }
        }
        throw new IllegalArgumentException();
    }
}
