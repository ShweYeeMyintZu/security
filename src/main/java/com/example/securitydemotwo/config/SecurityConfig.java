package com.example.securitydemotwo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        AuthenticationManagerBuilder authenticationManger=
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManger.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        http.httpBasic();

        http.authorizeHttpRequests().anyRequest().authenticated();
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        var uds=new InMemoryUserDetailsManager();
        var user1= User.withUsername("Mary").password("12345").authorities("read").build();
        var user2=User.withUsername("John").password("2468").authorities("write").build();
        uds.createUser(user1);
        uds.createUser(user2);
        return uds;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
