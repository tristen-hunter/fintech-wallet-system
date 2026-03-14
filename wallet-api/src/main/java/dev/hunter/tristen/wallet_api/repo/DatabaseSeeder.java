package dev.hunter.tristen.wallet_api.repo; // Ensure this matches your project!

 // Import your models
import dev.hunter.tristen.wallet_api.model.Transaction;
import dev.hunter.tristen.wallet_api.model.User;
import dev.hunter.tristen.wallet_api.model.Wallet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final WalletRepository walletRepo;
    private final TransactionRepository transRepo;

    public DatabaseSeeder(UserRepository userRepo, WalletRepository walletRepo, TransactionRepository transRepo) {
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
        this.transRepo = transRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0) {
            // 1. Create Users
            User tristen = new User();
            tristen.setUserName("Tristen");
            tristen.setEmail("tristen@huntermedia.com");
            tristen.setCreatedAt(LocalDateTime.now());

            User sarah = new User();
            sarah.setUserName("Sarah J");
            sarah.setEmail("sarah@example.com");
            sarah.setCreatedAt(LocalDateTime.now());

            userRepo.saveAll(List.of(tristen, sarah));

            // 2. Create Wallets
            Wallet tWalletZar = new Wallet(tristen, new BigDecimal("5000.00"), "ZAR");
            tWalletZar.setCreatedAt(LocalDateTime.now());

            Wallet sWalletZar = new Wallet(sarah, new BigDecimal("12000.00"), "ZAR");
            sWalletZar.setCreatedAt(LocalDateTime.now());

            walletRepo.saveAll(List.of(tWalletZar, sWalletZar));

            // 3. Create a Transaction
            Transaction t1 = new Transaction(tWalletZar, sWalletZar, new BigDecimal("250.00"));
            // Note: If you have a @PrePersist in Transaction for timestamp,
            // you don't need to set it manually here.
            transRepo.save(t1);

            System.out.println(">> Wallet Database seeded with Users, Wallets, and Transactions!");
        }
    }
}