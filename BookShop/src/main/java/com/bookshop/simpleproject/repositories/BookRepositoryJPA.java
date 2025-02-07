package com.bookshop.simpleproject.repositories;

import com.bookshop.simpleproject.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BookRepositoryJPA extends JpaRepository<Book, Long> {
}
