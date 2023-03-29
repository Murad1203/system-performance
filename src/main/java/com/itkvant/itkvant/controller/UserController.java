package com.itkvant.itkvant.controller;

import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.model.Wallet;
import com.itkvant.itkvant.service.UserService;
import com.itkvant.itkvant.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        long id = ((User) authentication.getPrincipal()).getId();
        Wallet wallet = walletService.findBYUserId(id);
        model.addAttribute("balance" , wallet.getBalance());
        return "index";
    }


    @PostMapping("/sign-up")
    public String register(User user) {
        log.info("-------"  + user);
        userService.signUpUser(user);
        return "redirect:/login";
    }
    @GetMapping("/sign-up")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
