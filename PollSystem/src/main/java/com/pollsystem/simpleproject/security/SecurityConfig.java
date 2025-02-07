package com.pollsystem.simpleproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        //http.csrf(customizer -> customizer.disable());
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("sessionInfo","Registration", "Login")
                        .permitAll());
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        //STATELESS mi mantiene lo stato del form login in continuazione
        //http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    //utilizzo questo servizio provider e utilizzo il mio custom userdetailservice
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //provider need to connect to databse to get data
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(myUserDetailService); //non uso il bean creato qui sotto
        return provider;
    }

    //devo gestire l authenticationmanager -> lui gestisce le sessioni di autenticazione behind the scene
    //devo ottenere l authentication manager e gestirlo
    //authmanager "parla" col authprovider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //@Bean
    //service for login and authentication --> roba vecchia e deprecata: si utilizza invece loadUserbyUsername
    //public UserDetailsService userDetailsService(){
//
    //    List<UserDetails> users = GetUserDetailsList();
    //    //UserDetails admin = getUserDetails("admin","admin123", "ADMIN");
    //    //users.add(admin);
    //    System.out.println(Arrays.toString(users.toArray()));
    //    //inserisco la lista di utenti che ho nel db come lista di utenti
    //    return new InMemoryUserDetailsManager(users);
    //    //return new MyUserDetailService();
    //}

    //private List<UserDetails> GetUserDetailsList() {
    //    //sto creando una lista di utenti con il relativo ruolo, che potranno accedere alla login
    //    //deprecato - solo per test
    //    return new ArrayList<>(userService
    //            .GetListUser()
    //            .stream()
    //            .map(user -> {
    //                return User
    //                        //deprecato - solo per test
    //                        .withDefaultPasswordEncoder()
    //                        .username(user.getUsername())
    //                        .password(user.getPassword())
    //                        .roles("USER") //optional
    //                        .build();
    //            })
    //            .toList());
    //}
//
    //private static UserDetails getUserDetails(String username, String password, String role) {
    //    return User
    //            //deprecato - solo per test
    //            .withDefaultPasswordEncoder()
    //            .username(username)
    //            .password(password)
    //            .roles(role) //optional
    //            .build();
    //    //in questo modo si creano gli utenti
    //}

}