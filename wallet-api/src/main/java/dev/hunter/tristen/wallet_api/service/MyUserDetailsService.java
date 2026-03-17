package dev.hunter.tristen.wallet_api.service;

import dev.hunter.tristen.wallet_api.model.Users;
import dev.hunter.tristen.wallet_api.repo.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public MyUserDetailsService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    // Authenticate the user (find and verify credentials)
    @Override
    public UserDetails loadUserByUsername(@NonNull String email)
            throws UsernameNotFoundException {

        // Find, verify and return the user based on their email
        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        // Wrap in the Spring Security User object
        return new User(
                user.getEmail(),
                user.getPasswordHash(),
                List.of()
        );

    }

}
