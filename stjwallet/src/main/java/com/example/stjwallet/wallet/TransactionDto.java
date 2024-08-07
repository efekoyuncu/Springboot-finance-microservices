package com.example.stjwallet.wallet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long senderCustomerId;
    private Long recipientCustomerId;
    private BigDecimal amount;
}
