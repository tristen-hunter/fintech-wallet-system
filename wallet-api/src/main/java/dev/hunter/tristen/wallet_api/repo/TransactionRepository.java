package dev.hunter.tristen.wallet_api.repo;

import dev.hunter.tristen.wallet_api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    //
    List<Transaction> findBySenderWalletIdOrReceiverWalletId(UUID senderId, UUID receiverId);

}
