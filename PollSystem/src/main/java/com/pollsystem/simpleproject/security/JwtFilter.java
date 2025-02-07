package com.pollsystem.simpleproject.security;

import com.pollsystem.simpleproject.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Override
    //customfilter added before the UsernamePasswordAuthenticationFilter
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //before the UPAF im getting the bearer token
        // Bearer ${token}
        String authHeader = request.getHeader("Authorization");
        String cookies = request.getHeader("Cookie");
        String token = null; //token is the 7th letter from the string "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYW5ndXN0YSIsImlhdCI6MTczODc2NTg4NiwiZXhwIjoxNzM4NzY2MDA2fQ.pUlNSXflJ2BES2MCFp_3pECHLHMxwR8lLhGBWQS7NDI"
        String username = null;
        System.out.println("JwtFilter - AuthHeader = " + authHeader);
        System.out.println("JwtFilter - Cookies = " + cookies);

        //try{
            if (authHeader != null && authHeader.startsWith("Bearer")){
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
            //controllo gia qui che l'utente non sia un campo vuoto e IMPORTANTE controllo che non sia gia autenticato
            if (username == null) {
                System.out.println("JwtFilter - Utente insesistente");
            }else{
                if (SecurityContextHolder.getContext().getAuthentication() == null){
                    //cerco di ottenere l'oggetto userdetails con tutte le info dell utente
                    UserDetails userdetails = myUserDetailService.loadUserByUsername(username);
                    //System.out.println("JwtFilter - Utente è ok e LOGGATO: " + userdetails.getUsername());
                    if (jwtService.validateToken(token, userdetails.getUsername())) {
                        System.out.println("JwtFilter - token valido");
                        //se token e valido adesso si passa al filtro UPAF
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //set authentication detail for user -> logged
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                    System.out.println("JwtFilter - Login ok dal JWT Filter!");
                }else{
                    System.out.println("JwtFilter - Utente è gia LOGGATO");
                }
            }
            filterChain.doFilter(request,response);
        //}catch (){}
        //catch (ExpiredJwtException e ){
        //    System.out.println("JwtFilter - JWT Scaduto!");
        //}catch (Exception e){
        //    System.out.println("JwtFilter - Other Error!");
        //}
    }
}
