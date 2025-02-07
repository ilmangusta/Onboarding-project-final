package com.pollsystem.simpleproject.security;

import com.pollsystem.simpleproject.domain.Users;
import com.pollsystem.simpleproject.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //effettua gia la query nel db per username
        Users user = usersRepository.findByUsername(username);
        if (user == null){
            System.out.println("MyUserDetailService - User not found in the database");
            throw new UsernameNotFoundException("Username not found");
        }else{
            System.out.println(("MyUserDetailService - Username ok in db: " + user.getUsername()));
        }
        //collection con i ruoli -> optional
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        ///authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("USER"));
        //user.getRoles().forEach( role -> {
        //  authorities.add(new SimpleGrantedAuthority(role.getName()));
        // })
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), authorities );
    }
}
