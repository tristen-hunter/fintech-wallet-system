package dev.hunter.tristen.wallet_api.domain.wallet;

import dev.hunter.tristen.wallet_api.dto.WalletCreateDTO;
import dev.hunter.tristen.wallet_api.dto.WalletResponseDTO;
import dev.hunter.tristen.wallet_api.domain.user.Users;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import dev.hunter.tristen.wallet_api.domain.user.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepo;
    private final UserRepository userRepo;

    public WalletService(WalletRepository walletRepo, UserRepository userRepo){
        this.walletRepo = walletRepo;
        this.userRepo = userRepo;
    }

    // [ADMIN] Return all Wallets from the DB
    public List<WalletResponseDTO> getWallets(){

        return walletRepo.findAll()
                .stream()
                .map(wallet -> new WalletResponseDTO(
                        wallet.getId(),
                        wallet.getUser().getId(),
                        wallet.getBalance(),
                        wallet.getCurrency(),
                        wallet.getCreatedAt()
                ))
                .toList();
    }



    // [USER] for a user to create a new wallet
    public WalletResponseDTO createWallet(@NonNull WalletCreateDTO newWalletDTO){
        // 1. Fetch user
        Users user = userRepo.findById(newWalletDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User ID Does Not Exist!"));

        // 2. Create Wallet Object
        Wallet newWallet = new Wallet();

        newWallet.setUser(user);
        newWallet.setBalance(BigDecimal.ZERO);
        newWallet.setCurrency(newWalletDTO.getCurrency());

        // 3. Store Wallet in the DB
        Wallet savedWallet = walletRepo.save(newWallet);

        // 4. Map the wallet to a DTO (return that)
        return new WalletResponseDTO(
                savedWallet.getId(),
                savedWallet.getUser().getId(),
                savedWallet.getBalance(),
                savedWallet.getCurrency(),
                savedWallet.getCreatedAt()
        );
    }

    // [USER] get all a users wallets and display them in a list of cards
    public List<WalletResponseDTO> getUserWallets(UUID userId){
        return walletRepo.findWalletsByUserId(userId)
                .stream()
                .map(wallet -> new WalletResponseDTO(
                        wallet.getId(),
                        wallet.getUser().getId(),
                        wallet.getBalance(),
                        wallet.getCurrency(),
                        wallet.getCreatedAt()
                ))
                .toList();
    }

    // [USER] Return a wallet by an ID (path variable) - for a user to view a specific wallets attributes
    public WalletResponseDTO getWalletById(UUID walletId){
        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet Does Not Exist"));

        return new WalletResponseDTO(
                wallet.getId(),
                wallet.getUser().getId(),
                wallet.getBalance(),
                wallet.getCurrency(),
                wallet.getCreatedAt()
        );
    }


    // [SYSTEM] for isolating wallets
    public LockedWallets lockAndFetchWallets(@NonNull UUID idA, UUID idB){
        // 1. Sort the IDs to prevent deadlocks
        UUID firstId = idA.compareTo(idB) < 0 ? idA : idB;
        UUID secondId = idA.compareTo(idB) < 0 ? idB : idA;

        // 2. Lock them in the DB (Pessimistic Write Lock)
        // The lock stays active until the @Transactional method in the Service finishes!
        Wallet first = walletRepo.findByIdForUpdate(firstId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + firstId));
        Wallet second = walletRepo.findByIdForUpdate(secondId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + secondId));

        return new LockedWallets(first, second);
    }

}
