package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_wallet_id", nullable = false)
    private Wallet senderWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_wallet_id", nullable = false)
    private Wallet receiverWallet;

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

    public Transaction(Wallet senderWalletId, Wallet receiverWalletId, BigDecimal amount) {
        this.senderWallet = senderWallet;
        this.receiverWallet = receiverWallet;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
    }

    // ID
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    // Sender Wallet
    public Wallet getSenderWalletId() {
        return senderWallet;
    }
    public void setSenderWalletId(Wallet senderWallet) { this.senderWallet = senderWallet; }

    // Receiver Wallet
    public Wallet getReceiverWalletId() { return receiverWallet; }
    public void setReceiverWalletId(Wallet receiverWallet) {
        this.receiverWallet = receiverWallet;
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