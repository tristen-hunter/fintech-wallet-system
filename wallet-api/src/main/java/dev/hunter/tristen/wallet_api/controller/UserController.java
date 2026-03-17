package dev.hunter.tristen.wallet_api.controller;

import dev.hunter.tristen.wallet_api.dto.UserCreateDTO;
import dev.hunter.tristen.wallet_api.dto.UserFetchDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import dev.hunter.tristen.wallet_api.service.UserService;

import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService){
        this.userService = userService;
    }

    // POST api/users
    // [USER] Adds a new user through the service layer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserFetchDTO addUser(@RequestBody UserCreateDTO newUserDTO){
        return userService.createUser(newUserDTO);
    }

    // [SYSTEM] This is how a users profile is populated - their username is matched to their ID and the info is returned here
    @GetMapping("/{id}")
    public UserFetchDTO getUserById(@PathVariable UUID id){
        return userService.getUserById(id);
    }


}

