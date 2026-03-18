package dev.hunter.tristen.wallet_api.domain.wallet;

import dev.hunter.tristen.wallet_api.domain.user.Users;
import dev.hunter.tristen.wallet_api.domain.transaction.Transaction;
import jakarta.persistence.*;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.ArrayList;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="wallets")
public class Wallet {

    // Primary key
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // Relationships with Transactions
    @OneToMany(mappedBy = "senderWallet")
    private List<Transaction> sentTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiverWallet")
    private List<Transaction> receivedTransactions = new ArrayList<>();


    // create a date and time at the exact moment the entity is created
    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    ///  Business logic

    // Inside Wallet.java (or a Validator class)
    public static void validateDifferentWallets(@NonNull UUID senderId, UUID receiverId) {
        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Cannot send money to the same wallet.");
        }
    }

    // Subtracting from balances
    public void debit(@NonNull BigDecimal amount){  // Sender of the money
        if (amount.compareTo(this.balance) > 0){
            throw new RuntimeException("balance is too low");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount){  // Receiver of the money
        this.balance = this.balance.add(amount);
    }



    public Wallet(){

    }

    public Wallet(Users user, BigDecimal balance, String currency){
        this.user = user;
        this.balance = balance;
        this.currency = currency;
    }

    // ID
    public UUID getId(){ return id; }

    public Users getUser(){ return user; }
    public void setUser(Users user){ this.user = user; }

    // Balance
    public BigDecimal getBalance(){ return balance; }
    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    // Currency
    public String getCurrency(){ return currency; }
    public void setCurrency(String currency){
        this.currency = currency;
    }

    // Created At
    public LocalDateTime getCreatedAt(){ return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }


    // Getters for Transactions
    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }
    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

}