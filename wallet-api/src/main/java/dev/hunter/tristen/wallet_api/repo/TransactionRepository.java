package dev.hunter.tristen.wallet_api.repo;

import dev.hunter.tristen.wallet_api.model.Transaction;
import dev.hunter.tristen.wallet_api.model.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // Finds a wallet regardless of whether it was the sender or receiver
    List<Transaction> findBySenderWalletIdOrReceiverWalletId(UUID senderId, UUID receiverId);

}
