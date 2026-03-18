package dev.hunter.tristen.wallet_api.domain.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // Finds a wallet regardless of whether it was the sender or receiver
    List<Transaction> findBySenderWalletIdOrReceiverWalletId(UUID senderId, UUID receiverId);

}
