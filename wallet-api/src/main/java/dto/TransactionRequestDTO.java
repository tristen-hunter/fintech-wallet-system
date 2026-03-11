package dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    @NotNull(message = "Sender wallet ID is required")
    private Long senderWalletId;

    @NotNull(message = "Receiver wallet ID is required")
    private Long receiverWalletId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 12, fraction = 2, message = "Amount can only have up to 2 decimal places")
    private BigDecimal amount;

    public TransactionRequestDTO(){}

    // Getters
    public Long getSenderWalletId(){ return senderWalletId; }
    public Long getReceiverWalletId(){ return receiverWalletId; }
    public BigDecimal getAmount(){ return amount; }

    // Setters
    public void setSenderWalletId(Long senderWalletId){
        this.senderWalletId = senderWalletId;
    }
    public void setReceiverWalletId(Long receiverWalletId){
        this.receiverWalletId = receiverWalletId;
    }
    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }
}
