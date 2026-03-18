package dev.hunter.tristen.wallet_api.domain.user;

import dev.hunter.tristen.wallet_api.dto.UserCreateDTO;
import dev.hunter.tristen.wallet_api.dto.UserFetchDTO;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // Constructor injection
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // [USER] Create a new user and store them in the DB
    public UserFetchDTO createUser(@NonNull UserCreateDTO newUserDTO){
        // 1. Validate email doesn't exist
        if (userRepository.existsByEmail(newUserDTO.getEmail())){
            throw new RuntimeException("Email already exists!");
        }

        // 2. Create the User object and set its info equal to the DTO
        Users newUser = new Users();
        newUser.setUserName(newUserDTO.getUserName());
        newUser.setEmail(newUserDTO.getEmail());

        // 3. Hash password (salted, one-way)
        newUser.setPasswordHash(passwordEncoder.encode(newUserDTO.getPassword()));

        // 4. Pass the User entity to the Repo and return it
        Users savedUser = userRepository.save(newUser);

        // 5. Mapping Entity -> DTO
        return new UserFetchDTO(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }

    // [SYSTEM] Fetch a user by their ID - for populating a users profile after login
    public UserFetchDTO getUserById(UUID id){
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));;

        return new UserFetchDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}