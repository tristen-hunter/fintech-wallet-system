package model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="wallets")
public class Wallet {

    // Primary key
    private UUID id;

    @Column(nullable = false)
    private UUID userId; // Foreign key

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // create a date and time at the exact moment the entity is created
    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Wallet(){

    }

    public Wallet(UUID userId, BigDecimal balance, String currency){
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

    // ID
    public UUID getID(){ return id; }

    // userId
    public UUID getUserId(){ return userId; }

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

}