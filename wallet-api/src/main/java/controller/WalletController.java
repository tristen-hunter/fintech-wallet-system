package controller;

import dto.WalletCreateDTO;
import dto.WalletResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.WalletService;

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
}
