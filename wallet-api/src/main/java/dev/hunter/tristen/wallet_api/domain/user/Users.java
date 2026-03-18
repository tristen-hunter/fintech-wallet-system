package dev.hunter.tristen.wallet_api.domain.user;

import dev.hunter.tristen.wallet_api.domain.wallet.Wallet;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
public class Users {

    // Object Variables
    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wallet> wallets = new ArrayList<>();

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false) // enforces unique emails
    private String email;

    private String passwordHash; // Hash using built in method

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // create a date and time at the exact moment the entity is created
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructor for JPA
    public Users(){

    }

    // Constructor to create users
    public Users(String userName, String email, String passwordHash){
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
    }


    public UUID getId(){ return id; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    // Username (getters and setters)
    public String getUserName(){ return userName; }
    public void setUserName(String userName){ this.userName = userName; }

    // Email (getters and setters)
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    // Password (getters and setters)
    public String getPasswordHash(){ return passwordHash; }
    public void setPasswordHash(String pass){ this.passwordHash = pass; }
}