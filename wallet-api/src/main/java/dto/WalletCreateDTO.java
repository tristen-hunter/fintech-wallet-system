package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class WalletCreateDTO {

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotBlank(message = "Currency Code is required")
    @Size(min = 3, max = 3, message = "Currency Code must be exactly 3 characters")
    @Pattern(regexp = "^[A-Z]+$", message = "Currency Code must be uppercase only")
    private String currency;

    public WalletCreateDTO(){}

    public WalletCreateDTO(Long userId, String currency){
        this.userId = userId;
        this.currency = currency;
    }

    public Long getUserId(){ return userId; }
    public String getCurrency(){ return currency; }

    public void setUserId(Long id){ this.userId = id; }
    public void setCurrency(String currency){ this.currency = currency; }
}
