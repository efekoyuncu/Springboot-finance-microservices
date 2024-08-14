package com.example.stjbackend.customer;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private Long id;
    private Long customerId;
    private BigDecimal balance;
}
