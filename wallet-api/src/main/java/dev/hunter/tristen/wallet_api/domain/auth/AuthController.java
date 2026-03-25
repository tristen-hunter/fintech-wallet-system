package dev.hunter.tristen.wallet_api.domain.auth;

import dev.hunter.tristen.wallet_api.dto.UserCreateDTO;
import dev.hunter.tristen.wallet_api.dto.UserFetchDTO;
import dev.hunter.tristen.wallet_api.dto.UserLoginDTO;
import dev.hunter.tristen.wallet_api.domain.user.Users;
import dev.hunter.tristen.wallet_api.domain.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    // Spring Security hub for verifying a user sent from the DB
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepo,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
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

    @GetMapping("/me")
    public UserFetchDTO getCurrentUser(Authentication auth){

        // Verify authentication
        // If no auth -> USER IS NOT LOGGED IN
        if (auth == null || !auth.isAuthenticated()){
            throw new RuntimeException("Not AuthenticTED"); // RETURNS 500
        }
            String email = auth.getName();

        Users user = userRepo.findByEmail(email)
                .orElseThrow();

        return new UserFetchDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }


    // Sign Up A User
    @PostMapping("/signup")
    public UserFetchDTO signup(
            @RequestBody @NonNull
            UserCreateDTO newUserDTO,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 1. Map DTO to Entity & Encode Password
        Users newUser = new Users();
        newUser.setUserName(newUserDTO.getUserName());
        newUser.setEmail(newUserDTO.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(newUserDTO.getPassword()));
        // Set any other defaults like Roles here



        // 2. Save to Database
        Users savedUser = userRepo.save(newUser);

        // 3. Manually Authenticate the user so they don't have to login immediately after signing up
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")) // Use a clean list here
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 4. Persist the Security Context to the Session
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Explicitly save the context
        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

        // 5. Return the Fetch DTO
        return new UserFetchDTO(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }
}
