package com.week4.assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.week4.assignment.service.UserService;

@Configuration
public class SecurityConfig{

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSucessHandler;

    private final UserService customUserDetailsService;

    public SecurityConfig(UserService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        .csrf().disable()
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login","/signup").permitAll()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/user/**").hasRole("USER")
        .anyRequest().authenticated()
        )

        .formLogin(form -> form
    .loginPage("/login")
    .successHandler(customAuthenticationSucessHandler)
    .failureUrl("/login?error")
    .permitAll()
    )

        .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        );

        return http.build();       
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }
}
