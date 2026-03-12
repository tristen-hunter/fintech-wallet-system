package service;

import dto.TransactionRequestDTO;
import dto.TransactionResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import model.Wallet;
import model.Transaction;
import repo.TransactionRepository;
import repo.WalletRepository;
import org.springframework.transaction.TransactionStatus;

@Transactional
public class TransactionService {

    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;

    public TransactionService(WalletRepository walletRepo, TransactionRepository transactionRepo){
        this.walletRepo = walletRepo;
        this.transactionRepo = transactionRepo;
    }

    public TransactionResponseDTO createTransaction(@NotNull TransactionRequestDTO newTransactionDTO){
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
                savedTransaction.getSenderWallet().getID(),
                savedTransaction.getReceiverWallet().getID(),
                savedTransaction.getAmount(),
                savedTransaction.getStatus(),
                savedTransaction.getCreatedAt()
        );
    }
}