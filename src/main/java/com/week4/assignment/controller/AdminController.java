package com.week4.assignment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.week4.assignment.model.User;
import com.week4.assignment.repository.UserRepo;
import com.week4.assignment.service.AdminService;
import com.week4.assignment.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;

    @Autowired
    UserService userService;

    

   
    @GetMapping("/users")
    public String adminDashboard(Model model){
        List<User> users = service.getAllUsers();    
        model.addAttribute("users", users);
        return "admindashboard";
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam String keyword, Model model){
        List<User> users = service.searchUsers(keyword); 
        model.addAttribute("users", users);
        return "admindashboard";
    }

    @GetMapping("/editUser/{id}")
    public String getEditUser(@PathVariable("id") Long id, Model model){
        User user = service.getUserById(id);
        model.addAttribute("user", user);
        return "editUserForm";
    }
 
    @PostMapping("/editUser")
    public String editUserDetails(@ModelAttribute User user){
        User user1 = service.getUserById(user.getId());
        if(user1!=null){
            service.editUserDetails(user.getId(),user);  
        }
        else{
            System.out.println("User not found");
        }
        return "redirect:/admin/users";
    }
}
