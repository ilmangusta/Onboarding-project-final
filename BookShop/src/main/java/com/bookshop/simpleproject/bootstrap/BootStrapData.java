package com.bookshop.simpleproject.bootstrap;

import com.bookshop.simpleproject.domain.*;
import com.bookshop.simpleproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepositoryJPA BookRepositoryJPA;
    private final EditorRepository editorRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepositoryJPA bookRepository, EditorRepository editorRepository) {
        this.authorRepository = authorRepository;
        this.BookRepositoryJPA = bookRepository;
        this.editorRepository = editorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //loadLibrary();
    }

    public void loadLibrary(){
        System.out.println("Started in Bootstrap");

        Author Rowling =  new Author("J.K.", "Rowling");
        Author Orwell = new Author("George", "Orwell");
        Author Austen =   new Author("Jane", "Austen");
        Author Twain = new Author("Mark", "Twain");
        authorRepository.save(Twain);

        Editor Penguin = new Editor("Penguin Random House","1745 Broadway, New York, NY, USA");
        Editor HarperCollins = new Editor( "HarperCollins", "195 Broadway, California USA");
        Editor Macmillan = new Editor( "Macmillan Publishers", "London");

        Book Potter = new Book("Harry Potter and the Sorcerer's Stone", 26.0, 320, 50);
        Potter.setAuthor(Rowling);
        Potter.setEditor(Penguin);
        BookRepositoryJPA.save(Potter);
        Rowling.getBooks().add(Potter);
        Penguin.getBooks().add(Potter);
        authorRepository.save(Rowling);
        editorRepository.save(Penguin);


        Book ottantaquattro = new Book("1984", 14.99, 328, 30);
        ottantaquattro.setEditor(HarperCollins);
        ottantaquattro.setAuthor(Orwell);
        BookRepositoryJPA.save(ottantaquattro);
        Orwell.getBooks().add(ottantaquattro);
        HarperCollins.getBooks().add(ottantaquattro);
        authorRepository.save(Orwell);
        editorRepository.save(HarperCollins);

        Book Pride = new Book("Pride and Prejudice", 9.99, 279, 40);
        Pride.setAuthor(Austen);
        Pride.setEditor(Macmillan);
        BookRepositoryJPA.save(Pride);
        Austen.getBooks().add(Pride);
        Macmillan.getBooks().add(Pride);
        authorRepository.save(Austen);
        editorRepository.save(Macmillan);

        Iterable<Book> books = BookRepositoryJPA.findAll();
        for (Book book : books) {
            System.out.println(book.toString());
        }

        System.out.println("Number of author: " + authorRepository.count());
        System.out.println("Number of book in repo: " + BookRepositoryJPA.count());
        System.out.println("Number of books of author: "+Rowling.getFirstName() + " = " + Rowling.getBooks().size());
        System.out.println("Number of books of author: "+Orwell.getFirstName() + " = " + Orwell.getBooks().size());
        System.out.println("Number of books of author: "+Austen.getFirstName() + " = " + Austen.getBooks().size());
        System.out.println("Number of books of author: "+Twain.getFirstName() + " = " + Twain.getBooks().size());
    }
}
