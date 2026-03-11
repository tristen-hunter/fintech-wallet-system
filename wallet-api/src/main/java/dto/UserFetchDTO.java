package dto;

import java.time.LocalDateTime;

///  Used for returning a Users profile info (read only)
public class UserFetchDTO {
    private final Long id;
    private final String userName;
    private final String email;
    private final LocalDateTime createdAt;

    public UserFetchDTO(Long id, String userName, String email, LocalDateTime createdAt){
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId(){ return id; }
    public String getUserName(){ return userName; }
    public String getEmail(){ return email; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
}
