package com.pollsystem.simpleproject.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //private final AuthenticationManager authenticationManager;
//
    //public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    //    this.authenticationManager = authenticationManager;
    //}
//
    //@Override
    //public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    //    //return super.attemptAuthentication(request, response);
    //    String username = request.getParameter("username");
    //    String password = request.getParameter("password");
    //    log.info("Username: {} - Password: {}", username, password);
    //    //sto creando i token presi dalla sessione di login (attempt login), creo il token con le info e lo passo all authenticationmanager
    //    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
    //    return authenticationManager.authenticate(token);
    //}
//
    //@Override
    //protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    //    //super.successfulAuthentication(request, response, chain, authResult);
    //    //if successfull loginn -> assign token and refresh token to user
    //    String username = request.getParameter("username");
    //    String password = request.getParameter("password");
    //    log.info("Username: {} - Password: {}", username, password);
    //    //sto creando i token presi dalla sessione di login (attempt login), creo il token con le info e lo passo all authenticationmanager
    //    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
    //    //return authenticationManager.authenticate(token);
    //}
//
    //@Override
    //protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    //    super.unsuccessfulAuthentication(request, response, failed);
    //}
}
