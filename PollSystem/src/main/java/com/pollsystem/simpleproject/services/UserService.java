package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.model.LoginModelDTO;
import com.pollsystem.simpleproject.model.UsersDTO;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.List;
import java.util.Optional;


public interface UserService {

    String verifyCredential(LoginModelDTO login);

    boolean validtoken(Users user) throws ExpiredJwtException;

    String generateToken(Users user);

    Iterable<Users> findAll();

    boolean register(Users user);

    Users save(Users user);

    List<UsersDTO> GetListUserDTO();

    List<Users> GetListUser();

    UsersDTO GetUserDTO(Long id);

    Optional<Users> GetUser(Long id);

    UsersDTO GetUserDTObyUsername(String username);

    Users GetUserbyUsername(String username);

    int checkPasswordUser(String username, String password);

}
