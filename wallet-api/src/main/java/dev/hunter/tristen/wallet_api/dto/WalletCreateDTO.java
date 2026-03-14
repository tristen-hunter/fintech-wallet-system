package dev.hunter.tristen.wallet_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class WalletCreateDTO {

    @NotNull(message = "User ID is mandatory")
    private UUID userId;

    @NotBlank(message = "Currency Code is required")
    @Size(min = 3, max = 3, message = "Currency Code must be exactly 3 characters")
    @Pattern(regexp = "^[A-Z]+$", message = "Currency Code must be uppercase only")
    private String currency;

    public WalletCreateDTO(){}

    public WalletCreateDTO(UUID userId, String currency){
        this.userId = userId;
        this.currency = currency;
    }

    public UUID getUserId(){ return userId; }
    public String getCurrency(){ return currency; }

    public void setUserId(UUID id){ this.userId = id; }
    public void setCurrency(String currency){ this.currency = currency; }
}
