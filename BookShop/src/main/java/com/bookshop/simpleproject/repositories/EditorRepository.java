package com.bookshop.simpleproject.repositories;

import com.bookshop.simpleproject.domain.Editor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface EditorRepository extends JpaRepository<Editor,Long> {
}
