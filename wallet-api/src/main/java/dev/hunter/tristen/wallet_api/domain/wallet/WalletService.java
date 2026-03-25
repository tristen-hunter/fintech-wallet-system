package dev.hunter.tristen.wallet_api.domain.wallet;

import dev.hunter.tristen.wallet_api.common.conversion.ExchangeService;
import dev.hunter.tristen.wallet_api.dto.WalletCreateDTO;
import dev.hunter.tristen.wallet_api.dto.WalletResponseDTO;
import dev.hunter.tristen.wallet_api.domain.user.Users;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import dev.hunter.tristen.wallet_api.domain.user.UserRepository;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private final WalletRepository walletRepo;
    private final UserRepository userRepo;
    private final ExchangeService exchangeService;

    public WalletService(WalletRepository walletRepo,
                         UserRepository userRepo,
                         ExchangeService exchangeService){
        this.walletRepo = walletRepo;
        this.userRepo = userRepo;
        this.exchangeService = exchangeService;
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

    // [SYSTEM] Get all a users wallets and calculate total networth in USD
    public String getNetworth(UUID userId) {
        // 1. Fetch wallets
        List<WalletResponseDTO> userWallets = getUserWallets(userId);
        if (userWallets.isEmpty()) return "0.00";

        // 2. Identify currencies that aren't USD
        Set<String> needed = userWallets.stream()
                .map(WalletResponseDTO::getCurrency)
                .filter(c -> !c.equals("USD"))
                .collect(Collectors.toSet());

        // 3. Get rates from the ExchangeService (Business logic stays clean)
        Map<String, Double> rates = exchangeService.getRatesFor("USD", needed);

        // 4. Calculate total
        BigDecimal totalUsd = userWallets.stream()
                .map(w -> {
                    if (w.getCurrency().equals("USD")) return w.getBalance();

                    Double rate = rates.get(w.getCurrency());
                    if (rate == null) return w.getBalance(); // Or handle missing rate error

                    return w.getBalance().divide(BigDecimal.valueOf(rate), 2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalUsd.toPlainString();
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
