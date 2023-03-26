package com.itkvant.itkvant.controller;

import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.model.Wallet;
import com.itkvant.itkvant.service.UserService;
import com.itkvant.itkvant.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @GetMapping("/balance/{userId}")
    public Double getBalance(@PathVariable long userId) {
        Wallet wallet = walletService.findBYUserId(userId);
        return wallet.getBalance();
    }

}
