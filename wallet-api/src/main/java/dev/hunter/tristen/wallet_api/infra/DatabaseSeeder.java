package dev.hunter.tristen.wallet_api.infra;

import dev.hunter.tristen.wallet_api.domain.transaction.TransactionRepository;
import dev.hunter.tristen.wallet_api.domain.user.UserRepository;
import dev.hunter.tristen.wallet_api.domain.wallet.WalletRepository;
import dev.hunter.tristen.wallet_api.domain.transaction.Transaction;
import dev.hunter.tristen.wallet_api.domain.user.Users;
import dev.hunter.tristen.wallet_api.domain.wallet.Wallet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final WalletRepository walletRepo;
    private final TransactionRepository transRepo;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepo,
                          WalletRepository walletRepo,
                          TransactionRepository transRepo,
                          PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
        this.transRepo = transRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepo.count() > 0) {
            return;
        }

        System.out.println("Seeding wallet database...");

        // ---------------------
        // 1️⃣ Create Users
        // ---------------------

        Users tristen = new Users();
        tristen.setUserName("Tristen");
        tristen.setEmail("tristen@huntermedia.com");
        tristen.setPasswordHash(passwordEncoder.encode("pass123"));
        tristen.setCreatedAt(LocalDateTime.now());

        Users sarah = new Users();
        sarah.setUserName("Sarah J");
        sarah.setEmail("sarah@example.com");
        sarah.setPasswordHash(passwordEncoder.encode("pass123"));
        sarah.setCreatedAt(LocalDateTime.now());

        userRepo.saveAll(List.of(tristen, sarah));

        // ---------------------
        // 2️⃣ Create Wallets
        // ---------------------

        // Tristen has TWO wallets
        Wallet tristenZarWallet = new Wallet(tristen, new BigDecimal("5000.00"), "ZAR");
        tristenZarWallet.setCreatedAt(LocalDateTime.now());

        Wallet tristenUsdWallet = new Wallet(tristen, new BigDecimal("1500.00"), "USD");
        tristenUsdWallet.setCreatedAt(LocalDateTime.now());

        // Sarah has ONE wallet
        Wallet sarahZarWallet = new Wallet(sarah, new BigDecimal("12000.00"), "ZAR");
        sarahZarWallet.setCreatedAt(LocalDateTime.now());

        walletRepo.saveAll(List.of(tristenZarWallet, tristenUsdWallet, sarahZarWallet));

        // ---------------------
        // 3️⃣ Create Transactions
        // ---------------------

        Transaction t1 = new Transaction(
                tristenZarWallet,
                sarahZarWallet,
                new BigDecimal("250.00")
        );

        Transaction t2 = new Transaction(
                sarahZarWallet,
                tristenUsdWallet,
                new BigDecimal("100.00")
        );

        Transaction t3 = new Transaction(
                tristenUsdWallet,
                tristenZarWallet,
                new BigDecimal("50.00")
        );

        transRepo.saveAll(List.of(t1, t2, t3));

        // ---------------------
        // Dev login credentials
        // ---------------------

        System.out.println("Database seeded successfully!");
        System.out.println("Test login:");
        System.out.println("Email: tristen@huntermedia.com");
        System.out.println("Password: pass123");
    }
}