package com.pollsystem.simpleproject.services;

import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.mapper.UserMapper;
import com.pollsystem.simpleproject.model.LoginModelDTO;
import com.pollsystem.simpleproject.model.UsersDTO;
import com.pollsystem.simpleproject.repositories.UsersRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public String verifyCredential(LoginModelDTO login){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        if (authentication.isAuthenticated()){
            Users user = this.GetUserbyUsername(login.getUsername());
            String token = this.generateToken(user);
            System.out.println("User logged successfully: " + token);
            user.setToken(token);
            usersRepository.save(user);
            System.out.println("Utente loggato con successo - JWT: " + token);
            return ("Utente loggato con successo - JWT: " + token);
        }
        System.out.println("Login failed: " + authentication.getDetails().toString());
        return "Login Fallito";
    }

    public String verifyUserLogged(){
        return "ok";
    }

    @Override
    public boolean validtoken(Users user) throws ExpiredJwtException {
        return jwtService.validateToken(user.getToken(),user.getUsername());
    }

    @Override
    public String generateToken(Users user){
        return jwtService.generateToken(user);
    }

    @Override
    public Iterable<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public boolean register(Users user) {
        if (usersRepository.findByUsername(user.getUsername()) == null){
            user.setPassword( encoder.encode(user.getPassword()));
            usersRepository.save(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Users save(Users user) {
        usersRepository.save(user);
        return user;
    }

    @Override
    public List<UsersDTO> GetListUserDTO() {
        return usersRepository
                .findAll()
                .stream()
                .map(
                        user -> {
                            return userMapper.UserToUserDTO(user);
                        })
                .collect(Collectors.toList());
    }

    @Override
    public List<Users> GetListUser() {
        return new ArrayList<>(usersRepository.findAll());
    }

    @Override
    public UsersDTO GetUserDTO(Long id) {
        for (Users user: usersRepository.findAll()){
            if (Objects.equals(user.getId(), id)){
                return userMapper.UserToUserDTO(user);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Optional<Users> GetUser(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public UsersDTO GetUserDTObyUsername(String username){
        for (Users user: usersRepository.findAll()){
            if (Objects.equals(user.getUsername(), username)){
                return userMapper.UserToUserDTO(user);
            }
        }
        return null;
    }

    @Override
    public Users GetUserbyUsername(String username){
        for (Users user: usersRepository.findAll()){
            if (Objects.equals(user.getUsername(), username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public int checkPasswordUser(String username, String password){

        //System.out.println("Passord passata: " +password);
        //System.out.println("Password codificata: " + encoder.encode(password));

        Users user = this.GetUserbyUsername(username);
        if ( user == null) {
            return -1;
        }else{
            System.out.println("Passord passata: " +password);
            System.out.println("Password codificata: " + encoder.encode(password));
            System.out.println("Passowrd vera account: " + user.getPassword());
            System.out.println("Password decodificata: " + encoder.matches(password,user.getPassword()));
            if (encoder.matches(password,user.getPassword())){
            //if (user.getPassword().equals(password)){
                return 1;
            }else {
                return -2;
            }
        }
    }



}
