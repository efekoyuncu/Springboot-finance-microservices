package com.example.stjwallet.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByCustomerId(Long customerId);

    @Query(value = "SELECT w FROM Wallet w ORDER BY w.balance DESC")
    List<Wallet> findTopLoaders(int topN);
}

