package dev.hunter.tristen.wallet_api.domain.transaction;

import dev.hunter.tristen.wallet_api.dto.TransactionRequestDTO;
import dev.hunter.tristen.wallet_api.dto.TransactionResponseDTO;
import dev.hunter.tristen.wallet_api.dto.TransactionUserResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    // Constructor injecting the service layer
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    // [USER] Create a new Transaction between 2 wallets
    @PostMapping
    public TransactionResponseDTO getTransactions(@RequestBody TransactionRequestDTO newTransactionDTO){
        return transactionService.createTransaction(newTransactionDTO);
    }

    // [USER] Returns a specific Transaction by the ID
    @GetMapping("/{transactionId}")
    public TransactionResponseDTO getTransactionById(@PathVariable UUID transactionId){
        return transactionService.getTransactionById(transactionId);
    }

    // [USER] returns ALL Transactions (sent and received) for a specified wallet
    @GetMapping("/wallets/{walletId}")
    public List<TransactionResponseDTO> getWalletTransactions(@PathVariable UUID walletId){
        return transactionService.getWalletTransactions(walletId);
    }

    // [USER] Return all transactions for a specific user
    @GetMapping("/user/{userId}")
    public List<TransactionUserResponseDTO> getUsersTransactions(@PathVariable UUID userId){
        return transactionService.getUserTransactions(userId);
    }
}
