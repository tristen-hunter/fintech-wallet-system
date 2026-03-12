package service;

import dto.WalletCreateDTO;
import dto.WalletResponseDTO;
import model.Wallet;
import org.jspecify.annotations.NonNull;
import repo.UserRepository;
import repo.WalletRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletService {
//    It should handle:
//    - Create wallet
//    - Get wallet by ID
//    - Get all wallets for a user
//    - Check wallet balance
//
//    DTOs used
//    - CreateWalletRequest
//    - WalletResponse
//    - WalletBalanceResponse

    private WalletRepository walletRepo;
    private UserRepository userRepo;

    public WalletService(WalletRepository walletRepo, UserRepository userRepo){
        this.walletRepo = walletRepo;
        this.userRepo = userRepo;
    }

    public WalletResponseDTO createWallet(@NonNull WalletCreateDTO newWalletDTO){
        // 1. Ensure User ID exists
        if (!userRepo.existsById(newWalletDTO.getUserId())){
            throw new RuntimeException("User ID Does Not Exist!");
        }

        // 2. Create Wallet Object
        Wallet newWallet = new Wallet();

        newWallet.setBalance(BigDecimal.ZERO);
        newWallet.setCurrency(newWalletDTO.getCurrency());

        // 3. Store Wallet in the DB
        Wallet savedWallet = walletRepo.save(newWallet);

        // 4. Map the wallet to a DTO (return that)
        return new WalletResponseDTO(
                savedWallet.getID(),
                savedWallet.getUser().getId(),
                savedWallet.getBalance(),
                savedWallet.getCurrency(),
                savedWallet.getCreatedAt()
        );
    }
}
