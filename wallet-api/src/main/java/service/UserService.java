package service;

import dto.UserCreateDTO;
import dto.UserFetchDTO;
import model.User;
import model.Wallet;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repo.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // Constructor injection
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserFetchDTO createUser(@NonNull UserCreateDTO newUserDTO){
        // 1. Validate email doesn't exist
        if (userRepository.existsByEmail(newUserDTO.getEmail())){
            throw new RuntimeException("Email already exists!");
        }

        // 2. Create the User object and set its info equal to the DTO
        User newUser = new User();
        newUser.setUserName(newUserDTO.getUserName());
        newUser.setEmail(newUserDTO.getEmail());

        // 3. Hash password (salted, one-way)
        newUser.setPasswordHash(passwordEncoder.encode(newUserDTO.getPassword()));

        // 4. Pass the User entity to the Repo and return it
        User savedUser = userRepository.save(newUser);

        // 5. Mapping Entity -> DTO
        return new UserFetchDTO(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }
}