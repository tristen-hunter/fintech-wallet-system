package controller;

import dto.UserCreateDTO;
import dto.UserFetchDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor Injection
    public UserController(UserService userService){
        this.userService = userService;
    }

    // POST api/users
    // Adds a new user through the service layer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserFetchDTO addUser(@RequestBody UserCreateDTO newUserDTO){
        return userService.createUser(newUserDTO);
    }

}

