package com.pollsystem.simpleproject.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.pollsystem.simpleproject.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    //IMPORTANTE!! JPA repository usa la sintassi indBy, readBy, getBy per generare gia query nel db
    Users findByUsername(String username);
}
