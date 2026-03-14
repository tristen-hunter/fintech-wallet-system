package dev.hunter.tristen.wallet_api.controller;

import dev.hunter.tristen.wallet_api.dto.TransactionRequestDTO;
import dev.hunter.tristen.wallet_api.dto.TransactionResponseDTO;
import org.springframework.web.bind.annotation.*;
import dev.hunter.tristen.wallet_api.service.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    // Create a new Transaction between 2 wallets
    @PostMapping ("/transactions")
    public TransactionResponseDTO getTransactions(@RequestBody TransactionRequestDTO newTransactionDTO){
        return transactionService.createTransaction(newTransactionDTO);
    }

    // Returns a specific Transaction
    @GetMapping("/transactions/{transactionId}")
    public TransactionResponseDTO getTransactionById(@PathVariable UUID transactionId){
        return transactionService.getTransactionById(transactionId);
    }

    // returns ALL Transactions (sent and received) for a specified wallet
    @GetMapping("/wallets/{walletId}/transactions")
    public List<TransactionResponseDTO> getWalletTransactions(@PathVariable UUID walletId){
        return transactionService.getWalletTransactions(walletId);
    }
}
