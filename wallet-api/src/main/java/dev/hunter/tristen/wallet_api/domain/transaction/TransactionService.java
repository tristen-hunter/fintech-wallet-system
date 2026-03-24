package dev.hunter.tristen.wallet_api.domain.transaction;

import dev.hunter.tristen.wallet_api.domain.wallet.LockedWallets;
import dev.hunter.tristen.wallet_api.domain.wallet.Wallet;
import dev.hunter.tristen.wallet_api.domain.wallet.WalletService;
import dev.hunter.tristen.wallet_api.dto.TransactionRequestDTO;
import dev.hunter.tristen.wallet_api.dto.TransactionResponseDTO;
import dev.hunter.tristen.wallet_api.dto.TransactionUserResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.antlr.v4.runtime.misc.Triple;
import org.jspecify.annotations.NonNull;
import dev.hunter.tristen.wallet_api.domain.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Valid
@Transactional
@Service
public class TransactionService {

    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;
    private final TransactionFactory transactionFactory;
    private final WalletService walletService;


    // Constructor - injecting: the Wallet DB, the Transaction DB and the Ledger DB
    public TransactionService(
            WalletRepository walletRepo,
            TransactionRepository transactionRepo,
            TransactionFactory transactionFactory,
            WalletService walletService){
        this.walletRepo = walletRepo;
        this.transactionRepo = transactionRepo;
        this.transactionFactory = transactionFactory;
        this.walletService = walletService;
    }

    // [USER] For creating a new Transaction
    public TransactionResponseDTO createTransaction(@NotNull @NonNull TransactionRequestDTO newTransactionDTO){
        // 1. Validate Wallets are different
        Wallet.validateDifferentWallets(newTransactionDTO.getReceiverWalletId(), newTransactionDTO.getSenderWalletId());

        LockedWallets locked = walletService.lockAndFetchWallets(
                newTransactionDTO.getSenderWalletId(),
                newTransactionDTO.getReceiverWalletId()
        );

        // 2. Identify who is who (sorted by ID, not role)
        Wallet sender = locked.first().getId().equals(newTransactionDTO.getSenderWalletId()) ? locked.first() : locked.second();
        Wallet receiver = locked.first().getId().equals(newTransactionDTO.getReceiverWalletId()) ? locked.first() : locked.second();

        // 2.2 Update balances
        sender.debit(newTransactionDTO.getAmount());
        receiver.credit(newTransactionDTO.getAmount());

        // 3. Use the Factory to build the complex object
        Transaction newTransaction = transactionFactory.createCompletedTransaction(sender, receiver, newTransactionDTO.getAmount());

        // 4. Save (Cascading saves the ledger entries too)
        Transaction saved = transactionRepo.save(newTransaction);

        // 5. return
        return new TransactionResponseDTO(
                saved.getId(),
                saved.getSenderWallet().getId(),
                saved.getReceiverWallet().getId(),
                saved.getAmount(),
                saved.getStatus(),
                saved.getCreatedAt()
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


    // [USER] Return all a users Transactions, marked SENT RECEIVED
    public List<TransactionUserResponseDTO> getUserTransactions(UUID userId) {
        // repo already returns the list correctly mapped
        return transactionRepo.findHistoryByUserId(userId);
    }
}