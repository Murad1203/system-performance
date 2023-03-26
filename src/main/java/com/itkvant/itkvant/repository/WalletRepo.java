package com.itkvant.itkvant.repository;

import com.itkvant.itkvant.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userId);
}
