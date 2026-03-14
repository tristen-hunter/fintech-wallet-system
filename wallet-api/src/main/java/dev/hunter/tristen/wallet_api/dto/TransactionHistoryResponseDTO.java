package dev.hunter.tristen.wallet_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionHistoryResponseDTO {
    private UUID id;
    private UUID senderWalletId;
    private UUID receiverWalletId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime timestamp;

    // Manual No-Args Constructor (Required by Jackson for deserialization)
    public TransactionHistoryResponseDTO() {}

    // Manual All-Args Constructor (Used by your Service/Mapper)
    public TransactionHistoryResponseDTO(UUID id, UUID senderWalletId, UUID receiverWalletId,
                                         BigDecimal amount, String status, LocalDateTime timestamp) {
        this.id = id;
        this.senderWalletId = senderWalletId;
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    // --- Manual Getters ---
    public UUID getId() { return id; }
    public UUID getSenderWalletId() { return senderWalletId; }
    public UUID getReceiverWalletId() { return receiverWalletId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // --- Manual Setters ---
    public void setId(UUID id) { this.id = id; }
    public void setSenderWalletId(UUID senderWalletId) { this.senderWalletId = senderWalletId; }
    public void setReceiverWalletId(UUID receiverWalletId) { this.receiverWalletId = receiverWalletId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setStatus(String status) { this.status = status; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}