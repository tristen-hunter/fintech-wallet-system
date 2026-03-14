package dev.hunter.tristen.wallet_api.controller;

import dev.hunter.tristen.wallet_api.dto.WalletCreateDTO;
import dev.hunter.tristen.wallet_api.dto.WalletResponseDTO;
import org.springframework.web.bind.annotation.*;
import dev.hunter.tristen.wallet_api.service.WalletService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping
    public WalletResponseDTO addWallet(@RequestBody WalletCreateDTO newWalletDTO){
        return walletService.createWallet(newWalletDTO);
    }

    // Get all wallets for a certain user
    @GetMapping
    public List<WalletResponseDTO> getWallets(){
        return walletService.getWallets();
    }

    // Get a certain wallet
    @GetMapping("/{walletId}")
    public WalletResponseDTO getWalletById(@PathVariable UUID walletId){
        return walletService.getWalletById(walletId);
    }
}
