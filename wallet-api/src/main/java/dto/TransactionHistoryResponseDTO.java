package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryResponseDTO {
    private Long id;
    private Long senderWalletId;
    private Long receiverWalletId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime timestamp;

    // Manual No-Args Constructor (Required by Jackson for deserialization)
    public TransactionHistoryResponseDTO() {}

    // Manual All-Args Constructor (Used by your Service/Mapper)
    public TransactionHistoryResponseDTO(Long id, Long senderWalletId, Long receiverWalletId,
                                      BigDecimal amount, String status, LocalDateTime timestamp) {
        this.id = id;
        this.senderWalletId = senderWalletId;
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    // --- Manual Getters ---
    public Long getId() { return id; }
    public Long getSenderWalletId() { return senderWalletId; }
    public Long getReceiverWalletId() { return receiverWalletId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // --- Manual Setters ---
    public void setId(Long id) { this.id = id; }
    public void setSenderWalletId(Long senderWalletId) { this.senderWalletId = senderWalletId; }
    public void setReceiverWalletId(Long receiverWalletId) { this.receiverWalletId = receiverWalletId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setStatus(String status) { this.status = status; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}