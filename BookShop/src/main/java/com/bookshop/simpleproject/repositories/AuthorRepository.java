package com.bookshop.simpleproject.repositories;

import com.bookshop.simpleproject.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
