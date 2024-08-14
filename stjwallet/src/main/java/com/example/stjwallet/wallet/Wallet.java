package com.example.stjwallet.wallet;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // This is my primary key

    @Column(nullable = false, unique = true)
    private Long customerId;

    @Column(nullable = false)
    private BigDecimal balance;
}