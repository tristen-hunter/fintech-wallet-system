package dev.hunter.tristen.wallet_api.domain.wallet;

import dev.hunter.tristen.wallet_api.dto.WalletCreateDTO;
import dev.hunter.tristen.wallet_api.dto.WalletResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    // Constructor - injecting the WalletService Layer
    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    // [ADMIN] Get all wallets for a certain user
    @GetMapping("/{userId}")
    public List<WalletResponseDTO> getWallets(){
        return walletService.getWallets();
    }


    // [USER] - for a user to create a new wallet
    @PostMapping
    public WalletResponseDTO addWallet(@RequestBody WalletCreateDTO newWalletDTO){
        return walletService.createWallet(newWalletDTO);
    }

    // [USER] View all a users wallets
    @GetMapping("/{userId}")
    public List<WalletResponseDTO> getWalletsByUserId(@PathVariable UUID userId){
        return walletService.getUserWallets(userId);
    }

    // [USER] Get a certain wallets attributes (usually owned by a user)
    @GetMapping("/{walletId}")
    public WalletResponseDTO getWalletById(@PathVariable UUID walletId){
        return walletService.getWalletById(walletId);
    }
}
