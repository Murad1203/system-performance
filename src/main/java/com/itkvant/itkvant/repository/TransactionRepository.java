package com.itkvant.itkvant.repository;

import com.itkvant.itkvant.model.Transaction;
import com.itkvant.itkvant.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByWallet(Wallet wallet);
}
