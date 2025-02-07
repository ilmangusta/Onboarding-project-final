package com.bookshop.simpleproject.controller;

import com.bookshop.simpleproject.model.BookDTO;
import com.bookshop.simpleproject.domain.Book;
import com.bookshop.simpleproject.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BooksRestController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/Getbooks")
    public ResponseEntity<List<BookDTO>> getResponseBooks(Model model){
        return new ResponseEntity<List<BookDTO>>(bookService.GetListBooksDTO(),HttpStatus.OK);
    }

    @GetMapping(value = "/Getbooks/{bookId}")
    public ResponseEntity<BookDTO> getResponseBookId(@PathVariable("bookId") Long id){
        return new ResponseEntity<BookDTO>(bookService.GetBookDTO(id), HttpStatus.OK);
    }

    @PostMapping(value = "/Addbook")
    public ResponseEntity<Book> addBook(
            @RequestBody BookDTO bookDTO){
        Book book = new Book();
        book.setAuthor(null);
        book.setEditor(null);
        book.setQuantity(bookDTO.getQuantity());
        book.setTitle(bookDTO.getTitle());
        book.setPages(bookDTO.getPages());
        book.setPrice(bookDTO.getPrice());
        bookService.save(book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @PostMapping(value = "/Editbook/{bookId}/{quantity}")
    public ResponseEntity<Book> editQuantityBook(
            @PathVariable("bookId") Long id,
            @PathVariable("quantity") int quantity) throws Exception {
        Book book = bookService.findById(id);
        book.setQuantity(quantity);
        bookService.save(book);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @DeleteMapping(value = "/DeleteBook/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long id){
        bookService.deleteById(id);
    }

}
