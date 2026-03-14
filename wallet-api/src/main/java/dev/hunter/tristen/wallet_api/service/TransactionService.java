package dev.hunter.tristen.wallet_api.service;

import dev.hunter.tristen.wallet_api.dto.TransactionRequestDTO;
import dev.hunter.tristen.wallet_api.dto.TransactionResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import dev.hunter.tristen.wallet_api.model.Wallet;
import dev.hunter.tristen.wallet_api.model.Transaction;
import org.jspecify.annotations.NonNull;
import dev.hunter.tristen.wallet_api.repo.TransactionRepository;
import dev.hunter.tristen.wallet_api.repo.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class TransactionService {

    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;

    public TransactionService(WalletRepository walletRepo, TransactionRepository transactionRepo){
        this.walletRepo = walletRepo;
        this.transactionRepo = transactionRepo;
    }

    // For creating a new Transaction
    public TransactionResponseDTO createTransaction(@NotNull @NonNull TransactionRequestDTO newTransactionDTO){
        // 1. Verify Wallets exist
        Wallet sender = walletRepo.findById(newTransactionDTO.getSenderWalletId())
                .orElseThrow(()-> new RuntimeException("Sender wallet not found"));

        Wallet receiver = walletRepo.findById(newTransactionDTO.getReceiverWalletId())
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        // 2. Create Transaction Object
        Transaction newTransaction = new Transaction(
                sender,
                receiver,
                newTransactionDTO.getAmount()
        );

        // 3. Save to the DB
        Transaction savedTransaction = transactionRepo.save(newTransaction);

        return new TransactionResponseDTO(
                savedTransaction.getId(),
                savedTransaction.getSenderWallet().getId(),
                savedTransaction.getReceiverWallet().getId(),
                savedTransaction.getAmount(),
                savedTransaction.getStatus(),
                savedTransaction.getCreatedAt()
        );
    }

    // For Finding a specific transaction by Id
    public TransactionResponseDTO getTransactionById(UUID transactionId){
        Transaction transaction = transactionRepo.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction Not Found :("));

        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getSenderWallet().getId(),
                transaction.getReceiverWallet().getId(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );
    }

    // Return a list of Transactions pertaining to a certain Wallet ID
    public List<TransactionResponseDTO> getWalletTransactions(UUID walletId){
        return transactionRepo.findBySenderWalletIdOrReceiverWalletId(walletId, walletId)
                .stream()
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getSenderWallet().getId(),
                        transaction.getReceiverWallet().getId(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getCreatedAt()
                ))
                .toList();
    }
}