package com.bookshop.simpleproject.services;

import com.bookshop.simpleproject.domain.Book;
import com.bookshop.simpleproject.model.BookDTO;

import java.util.List;

public interface BookService {
    Iterable<Book> findAll();

    Book findById(Long Id) throws Exception;

    void save(Book book);

    void deleteById(Long id);

    List<Book> GetListBooks();

    List<BookDTO> GetListBooksDTO();

    BookDTO GetBookDTO(Long id);
}
