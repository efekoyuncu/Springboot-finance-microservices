package com.example.stjwallet.wallet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {
    private Long senderCustomerId;
    private Long recipientCustomerId;
    private BigDecimal amount;


    // Getters and Setters

    public Long getSenderCustomerId() {
        return senderCustomerId;
    }

    public void setSenderCustomerId(Long senderCustomerId) {
        this.senderCustomerId = senderCustomerId;
    }

    public Long getRecipientCustomerId() {
        return recipientCustomerId;
    }

    public void setRecipientCustomerId(Long recipientCustomerId) {
        this.recipientCustomerId = recipientCustomerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



}
