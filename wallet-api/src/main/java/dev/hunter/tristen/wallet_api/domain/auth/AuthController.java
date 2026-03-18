package dev.hunter.tristen.wallet_api.domain.auth;

import dev.hunter.tristen.wallet_api.dto.UserFetchDTO;
import dev.hunter.tristen.wallet_api.dto.UserLoginDTO;
import dev.hunter.tristen.wallet_api.domain.user.Users;
import dev.hunter.tristen.wallet_api.domain.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
    public UserFetchDTO login(@RequestBody @NonNull UserLoginDTO loginRequest, HttpServletRequest request){
        // DEBUG: Print what is being received
        System.out.println("Login Attempt for: " + loginRequest.getEmail());
        System.out.println("Raw Password provided: " + loginRequest.getPassword());

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                ));

        // CRITICAL: This is what "logs the user in" for the session
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

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
