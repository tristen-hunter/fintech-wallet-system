package dev.hunter.tristen.wallet_api.dto;

import dev.hunter.tristen.wallet_api.domain.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionResponseDTO {
    private UUID id;
    private UUID senderWalletId;
    private UUID receiverWalletId;
    private BigDecimal amount;
    private TransactionStatus status;
    private LocalDateTime timestamp;

    // Default constructor for JSON mapping
    public TransactionResponseDTO() {}

    // Full constructor - Used by your Service/Mapper to transform the Entity into this DTO
    public TransactionResponseDTO(UUID id, UUID senderWalletId, UUID receiverWalletId,
                                  BigDecimal amount, TransactionStatus status, LocalDateTime timestamp) {
        this.id = id;
        this.senderWalletId = senderWalletId;
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    // --- Getters ---
    public UUID getId() { return id; }
    public UUID getSenderWalletId() { return senderWalletId; }
    public UUID getReceiverWalletId() { return receiverWalletId; }
    public BigDecimal getAmount() { return amount; }
    public TransactionStatus getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // --- Setters ---
    public void setId(UUID id) { this.id = id; }
    public void setSenderWalletId(UUID senderWalletId) { this.senderWalletId = senderWalletId; }
    public void setReceiverWalletId(UUID receiverWalletId) { this.receiverWalletId = receiverWalletId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setStatus(TransactionStatus status) { this.status = status; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}