package com.example.stjwallet.wallet;

import com.example.stjwallet.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;


    public Wallet createWallet(Long customerId) {
        if (walletRepository.findByCustomerId(customerId).isPresent()) {
            throw new IllegalStateException("Wallet already exists for customer " + customerId);
        }
        Wallet wallet = new Wallet();
        wallet.setCustomerId(customerId);
        wallet.setBalance(BigDecimal.ZERO);
        Wallet savedWallet = walletRepository.save(wallet);
        System.out.println("Wallet created successfully for customer " + customerId); // Debugging line
        return savedWallet;
    }


    public Wallet getWalletByCustomerId(Long customerId) {
        return walletRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for customer: " + customerId));
    }

    @Transactional
    public Wallet loadMoney(Long customerId, BigDecimal balance) {
        Wallet wallet = getWalletByCustomerId(customerId);
        wallet.setBalance(wallet.getBalance().add(balance));
        return walletRepository.save(wallet);
    }

    @Transactional
    public void sendMoney(Long senderCustomerId, Long recipientCustomerId, BigDecimal amount) {
        Wallet senderWallet = walletRepository.findByCustomerId(senderCustomerId)
                .orElseGet(() -> createWallet(senderCustomerId));
        Wallet recipientWallet = walletRepository.findByCustomerId(recipientCustomerId)
                .orElseGet(() -> createWallet(recipientCustomerId));

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance in sender's wallet");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        recipientWallet.setBalance(recipientWallet.getBalance().add(amount));

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);
    }

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public List<Wallet> getTopLoaders(int topN) {
        return walletRepository.findTopLoaders(topN);
    }
}
