package dev.hunter.tristen.wallet_api.domain.transaction;

import dev.hunter.tristen.wallet_api.domain.wallet.Wallet;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private TransactionStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate(){
        timestamp = LocalDateTime.now();
    }

    // default JPA constructor
    public Transaction(){

    }

    // constructor
    public Transaction(Wallet senderWallet, Wallet receiverWallet, BigDecimal amount) {
        this.senderWallet = senderWallet;
        this.receiverWallet = receiverWallet;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
    }

    ///  Business Logic
    // For setting ledger entries
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LedgerEntry> ledgerEntries = new ArrayList<>();

    // The setter the Factory uses
    public void addLedgerEntry(LedgerEntry entry) {
        ledgerEntries.add(entry);
        entry.setTransaction(this); // This links the LedgerEntry to this Transaction
    }

    // getters
    public UUID getId() {
        return id;
    }
    public Wallet getSenderWallet() {
        return senderWallet;
    }
    public Wallet getReceiverWallet() { return receiverWallet; }
    public BigDecimal getAmount() {
        return amount;
    }
    public TransactionStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return timestamp;
    }
    public List<LedgerEntry> getLedgerEntries() {
        return ledgerEntries;
    }

    // setters
    public void setId(UUID id) {
        this.id = id;
    }
    public void setSenderWallet(Wallet senderWallet) { this.senderWallet = senderWallet; }
    public void setReceiverWallet(Wallet receiverWallet) {
        this.receiverWallet = receiverWallet;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

}