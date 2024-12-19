
package com.week4.assignment.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.week4.assignment.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {
 
    @Autowired
    UserRepo userRepo;

    public void saveUser(com.week4.assignment.model.User user) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(user.getPassword()));
    userRepo.save(user);
   }

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        com.week4.assignment.model.User user = userRepo.findByUsername(username)
                   .orElseThrow(()-> new UsernameNotFoundException("User not found:"+username));

        return User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole())
        .build();
    }
}
