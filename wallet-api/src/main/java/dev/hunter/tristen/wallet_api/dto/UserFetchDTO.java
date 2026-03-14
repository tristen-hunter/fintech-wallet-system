package dev.hunter.tristen.wallet_api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

///  Used for returning a Users profile info (read only)
public class UserFetchDTO {
    private final UUID id;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;

    public UserFetchDTO(UUID id, String userName, String email, LocalDateTime createdAt){
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId(){ return id; }
    public String getUserName(){ return userName; }
    public String getEmail(){ return email; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
}
