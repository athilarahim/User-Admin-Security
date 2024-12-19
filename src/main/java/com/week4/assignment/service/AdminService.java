package com.week4.assignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.week4.assignment.model.User;
import com.week4.assignment.repository.UserRepo;

@Service
public class AdminService{

    @Autowired
    UserRepo userRepo;

    public List<com.week4.assignment.model.User> getAllUsers(){
        return userRepo.findAll();
    }

    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }

    public List<User> searchUsers(String keyword) {
        return userRepo.searchUsers(keyword);
    }

    public User getUserById(Long id){
        return userRepo.findById(id).get();
    }

    public void editUserDetails(Long id, User user) {
        User usr = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        //orElseThrow is required to unwrap the Optional<User> type to User type
        usr.setUsername(user.getUsername());
        usr.setEmail(user.getEmail());
        usr.setRole(user.getRole());
        userRepo.save(usr);
    }

   
    

}
