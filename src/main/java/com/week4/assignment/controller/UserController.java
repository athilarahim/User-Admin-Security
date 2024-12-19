package com.week4.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.week4.assignment.model.User;
import com.week4.assignment.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    
    @Autowired
    UserService service;

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user){
        service.saveUser(user);
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Manually invalidates the session
        return "redirect:/login?logout";
    }
    
    @GetMapping("/user/home")
    public String displayHome(Model model){
        model.addAttribute("title", "Home Page");
        return "home";
    }
}
