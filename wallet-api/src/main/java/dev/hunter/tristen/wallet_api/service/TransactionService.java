package dev.hunter.tristen.wallet_api.service;

import dev.hunter.tristen.wallet_api.dto.TransactionRequestDTO;
import dev.hunter.tristen.wallet_api.dto.TransactionResponseDTO;
import dev.hunter.tristen.wallet_api.model.TransactionStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import dev.hunter.tristen.wallet_api.model.Wallet;
import dev.hunter.tristen.wallet_api.model.Transaction;
import org.jspecify.annotations.NonNull;
import dev.hunter.tristen.wallet_api.repo.TransactionRepository;
import dev.hunter.tristen.wallet_api.repo.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Valid
@Transactional
@Service
public class TransactionService {

    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;

    // Constructor - injecting: the Wallet DB and the Transaction DB
    public TransactionService(WalletRepository walletRepo, TransactionRepository transactionRepo){
        this.walletRepo = walletRepo;
        this.transactionRepo = transactionRepo;
    }


    // [USER] For creating a new Transaction
    public TransactionResponseDTO createTransaction(@NotNull @NonNull TransactionRequestDTO newTransactionDTO){
        if (newTransactionDTO.getSenderWalletId().equals(newTransactionDTO.getReceiverWalletId())) {
            throw new RuntimeException("Cannot send money to the same wallet.");
        }

        // 1. Order wallets UUID's & Lock them
        UUID firstId  = newTransactionDTO.getSenderWalletId();
        UUID secondId = newTransactionDTO.getReceiverWalletId();

        if (firstId.compareTo(secondId) > 0){
            firstId = newTransactionDTO.getReceiverWalletId();
            secondId = newTransactionDTO.getSenderWalletId();
        }

        // 2. Lock wallets into a consistent order
        walletRepo.findByIdForUpdate(firstId);
        walletRepo.findByIdForUpdate(secondId);

        // 3. Assign wallets to the sender or receiver IF they exist
        Wallet sender = walletRepo.findById(newTransactionDTO.getSenderWalletId()).orElseThrow();
        Wallet receiver = walletRepo.findById(newTransactionDTO.getReceiverWalletId()).orElseThrow();

        // 4. Create Transaction Object
        Transaction newTransaction = new Transaction(
                sender,
                receiver,
                newTransactionDTO.getAmount()
        );

        // 5. Validate Balance
        if (sender.getBalance().compareTo(newTransaction.getAmount()) < 0){
            throw new RuntimeException("Balance too low :(");
        } else if (newTransaction.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0){
            throw new RuntimeException("Transaction Amount Cannot be Negative or Zero");
        }

        // 6. Change wallet Balances (Calculate, set, save)
        BigDecimal newSenderBalance = sender.getBalance().subtract(newTransaction.getAmount());
        sender.setBalance(newSenderBalance);

        BigDecimal newReceiverBalance = receiver.getBalance().add(newTransaction.getAmount());
        receiver.setBalance(newReceiverBalance);

        // 7. Set Status
        newTransaction.setStatus(TransactionStatus.COMPLETED);

        // 8. Save to the DB
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


    // [USER] For returning a specific transactions attributes by using it's ID
        // USECASE: a user clicks "View Details" on the transaction in the list of transactions
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


    // [USER] Return a list of Transactions pertaining to a certain Wallet ID
        // USECASE: User clicks "View Transaction History" on their wallet card
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