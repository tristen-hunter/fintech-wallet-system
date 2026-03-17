package dev.hunter.tristen.wallet_api.repo;

import dev.hunter.tristen.wallet_api.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    List<Wallet> findWalletsByUserId(UUID userId);

}