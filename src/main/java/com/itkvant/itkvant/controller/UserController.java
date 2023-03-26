package com.itkvant.itkvant.controller;

import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @PostMapping("/sign-up")
    public String register(@RequestBody User user) {
        log.info("-------"  + user);
        userService.signUpUser(user);
        return "redirect:/login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
