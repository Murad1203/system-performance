package com.itkvant.itkvant.service;

import com.itkvant.itkvant.exception.InsufficientFundsException;
import com.itkvant.itkvant.exception.WalletNotFundsException;
import com.itkvant.itkvant.model.Transaction;
import com.itkvant.itkvant.model.User;
import com.itkvant.itkvant.model.Wallet;
import com.itkvant.itkvant.repository.TransactionRepository;
import com.itkvant.itkvant.repository.WalletRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WalletService {

    @Autowired
    private WalletRepo walletRepo;

    private TransactionRepository transactionRepository;


    public Wallet getWalletById(Long id) {
        Wallet wallet = null;
        Optional<Wallet> wallet1 = walletRepo.findById(id);

        if (wallet1.isPresent()) {
            wallet = wallet1.get();
        }
        return wallet;
    }

    public Wallet findBYUserId(Long userId) {
        return walletRepo.findByUserId(userId).orElseThrow(() -> new WalletNotFundsException("Wallet By User not funds with id: " + userId));
    }

    public Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);
        return walletRepo.save(wallet);
    }

    public List<Wallet> getAllWallets() {
        return walletRepo.findAll();
    }


    @Transactional
    public void addFunds(Long walletId, Double amount) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new WalletNotFundsException("Wallet not funds with id: " + walletId));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepo.save(wallet);
    }

    @Transactional
    public void withDrawFounds(Long walletId, Double amount) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new WalletNotFundsException("Wallet not found with id: " + walletId));

        if (wallet.getBalance() < amount) {
            throw new InsufficientFundsException("InsufficientFundsException funds in wallet id: " + walletId);
        }

        wallet.setBalance(wallet.getBalance() - amount);
        walletRepo.save(wallet);
    }

    public Double getBalance(Long walletId) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(() -> new WalletNotFundsException("Wallet not found with id: " + walletId));
        return wallet.getBalance();
    }


    public List<Transaction> getTransactionHistory(Long walletId) {
        Wallet wallet = walletRepo.findById(walletId).
                orElseThrow(() -> new WalletNotFundsException("Wallet not found with id: " + walletId));
        return transactionRepository.findAllByWallet(wallet);
    }

}
