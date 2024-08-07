package com.example.stjwallet.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        try {
            Wallet createdWallet = walletService.createWallet(wallet.getCustomerId());
            return ResponseEntity.ok(createdWallet);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/load")
    public ResponseEntity<?> loadMoney(@RequestBody Wallet wallet) {
        try {
            Wallet updatedWallet = walletService.loadMoney(wallet.getCustomerId(), wallet.getBalance());
            return ResponseEntity.ok(updatedWallet);
        } catch (Exception ex) {
            return new ResponseEntity<>("Failed to load money: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/send")
    public ResponseEntity<?> sendMoney(@RequestBody TransactionDto transactionDto) {
        try {
            walletService.sendMoney(transactionDto.getSenderCustomerId(), transactionDto.getRecipientCustomerId(), transactionDto.getAmount());
            return ResponseEntity.ok("Money sent successfully");
        } catch (Exception ex) {
            return new ResponseEntity<>("Failed to send money: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getAllWallets() {
        List<Wallet> wallets = walletService.getAllWallets();
        return ResponseEntity.ok(wallets);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getWalletByCustomerId(@PathVariable Long customerId) {
        try {
            Wallet wallet = walletService.getWalletByCustomerId(customerId);
            return ResponseEntity.ok(wallet);
        } catch (Exception ex) {
            return new ResponseEntity<>("Wallet not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/top-loaders")
    public ResponseEntity<List<Wallet>> getTopLoaders(@RequestParam int topN) {
        try {
            List<Wallet> topLoaders = walletService.getTopLoaders(topN);
            return ResponseEntity.ok(topLoaders);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static class WalletRequest {
        private Long customerId;

        public WalletRequest() {}

        public WalletRequest(Long customerId) {
            this.customerId = customerId;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }
    }


}
