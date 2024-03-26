package com.projekt.web.controllers;

import com.projekt.web.models.User;
import com.projekt.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/most-active-users")
    public String mostActiveUsers(Model model) {
        List<User> mostActiveUsers = userService.getMostActiveUsers();
        model.addAttribute("mostActiveUsers", mostActiveUsers);
        return "most-active-users";
    }
    @GetMapping("/faq")
    public String faq( ) {
        return "faq";
    }

    @GetMapping("/contact")
    public String contact( ) {
        return "contact";
    }
}
