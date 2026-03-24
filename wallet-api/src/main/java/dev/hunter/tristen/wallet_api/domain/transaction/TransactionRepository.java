package dev.hunter.tristen.wallet_api.domain.transaction;

import dev.hunter.tristen.wallet_api.dto.TransactionUserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    // Finds a wallet regardless of whether it was the sender or receiver
    List<Transaction> findBySenderWalletIdOrReceiverWalletId(UUID senderId, UUID receiverId);

    @Query("""
    SELECT new dev.hunter.tristen.wallet_api.dto.TransactionUserResponseDTO(
        t.id, 
        t.amount, 
        t.status, 
        t.timestamp,
        (CASE WHEN t.senderWallet.user.id = :userId THEN 'SENT' ELSE 'RECEIVED' END),
        (CASE WHEN t.senderWallet.user.id = :userId THEN t.receiverWallet.user.email ELSE t.senderWallet.user.email END),
        (CASE WHEN t.senderWallet.user.id = :userId THEN t.receiverWallet.id ELSE t.senderWallet.id END)
    )
    FROM Transaction t
    WHERE t.senderWallet.user.id = :userId 
    OR t.receiverWallet.user.id = :userId
    ORDER BY t.timestamp DESC
""")
    List<TransactionUserResponseDTO> findHistoryByUserId(@Param("userId") UUID userId);
}
