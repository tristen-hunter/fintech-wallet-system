package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private Long senderWalletId;

    @Column(nullable = false)
    private  Long receiverWalletId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private model.TransactionStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate(){
        timestamp = LocalDateTime.now();
    }

    // default JPA constructor
    public Transaction(){

    }

    public Transaction(Long senderWalletId, Long receiverWalletId, BigDecimal amount) {
        this.senderWalletId = senderWalletId;
        this.receiverWalletId = receiverWalletId;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
    }

    // ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    // Sender Wallet
    public Long getSenderWalletId() {
        return senderWalletId;
    }

    public void setSenderWalletId(Long senderWalletId) {
        this.senderWalletId = senderWalletId;
    }


    // Receiver Wallet
    public Long getReceiverWalletId() {
        return receiverWalletId;
    }

    public void setReceiverWalletId(Long receiverWalletId) {
        this.receiverWalletId = receiverWalletId;
    }


    // Amount
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    // Status
    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }


    // Created Timestamp
    public LocalDateTime getCreatedAt() {
        return timestamp;
    }
}