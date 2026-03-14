package dev.hunter.tristen.wallet_api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


// The goal is to
public class WalletResponseDTO {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;

    public WalletResponseDTO(UUID id, UUID userId, BigDecimal balance, String currency, LocalDateTime createdAt){
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }
    // --- GETTERS ---
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // --- SETTERS ---
    public void setId(UUID id) { this.id = id; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}