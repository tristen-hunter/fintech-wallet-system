package dev.hunter.tristen.wallet_api.controller;

import dev.hunter.tristen.wallet_api.dto.UserFetchDTO;
import dev.hunter.tristen.wallet_api.dto.UserLoginDTO;
import dev.hunter.tristen.wallet_api.model.Users;
import dev.hunter.tristen.wallet_api.repo.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;

    // Spring Security hub for verifying a user sent from the DB
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
    }
    // handles user login authentication
    @PostMapping("/login")
    public UserFetchDTO login(@RequestBody @NonNull UserLoginDTO loginRequest){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                ));

        Users user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow();

        return new UserFetchDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}
