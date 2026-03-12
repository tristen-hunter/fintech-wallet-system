package model;

import jakarta.persistence.*;

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
    private User user;

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

    public Wallet(User user, BigDecimal balance, String currency){
        this.user = user;
        this.balance = balance;
        this.currency = currency;
    }

    // ID
    public UUID getID(){ return id; }

    public User getUser(){ return user; }
    public void setUser(User user){ this.user = user; }

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