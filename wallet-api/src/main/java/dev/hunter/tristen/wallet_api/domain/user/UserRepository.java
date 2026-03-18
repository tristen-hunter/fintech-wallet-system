package dev.hunter.tristen.wallet_api.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    // For user registration
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

}
