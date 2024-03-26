package com.projekt.web.controllers;

import com.projekt.web.dto.RegisterDto;
import com.projekt.web.models.User;
import com.projekt.web.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {
    private UserService userService;
    public SecurityController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegisterDto user = new RegisterDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegisterDto user, BindingResult result, Model model){
        User existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty())
        {
            return "redirect:/register?fail";
        }
        User existingUserLogin = userService.findByLogin(user.getLogin());
        if(existingUserLogin != null && existingUserLogin.getLogin() != null && !existingUserLogin.getLogin().isEmpty())
        {
            return "redirect:/register?fail";
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/trips?successs";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login/process")
    public String login(@RequestParam("login") String login, @RequestParam("password") String password, HttpSession session, Model model) {
        User existingUser = userService.findByLogin(login);

        if (existingUser != null && existingUser.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", existingUser);
            String userRole = existingUser.getRoles().isEmpty() ? "" : existingUser.getRoles().get(0).getName();
            session.setAttribute("userRole", userRole);
            model.addAttribute("user", existingUser);
            return "redirect:/trips";
        } else {
            model.addAttribute("loginError", true);
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        session.removeAttribute("userRole");
        return "redirect:/trips";
    }
}


