package com.example.userserviceproject.controllers;

import com.example.userserviceproject.dtos.LoginRequestDto;
import com.example.userserviceproject.dtos.LogoutRequestDto;
import com.example.userserviceproject.dtos.SignUpRequestDto;
import com.example.userserviceproject.dtos.UserDto;
import com.example.userserviceproject.exceptions.UserNotFoundException;
import com.example.userserviceproject.models.Token;
import com.example.userserviceproject.models.User;
import com.example.userserviceproject.repositories.TokenRepository;
import com.example.userserviceproject.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private final TokenRepository tokenRepository;

    UserController(UserService userService,
                   TokenRepository tokenRepository){
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto){
        User user = userService.signup(requestDto.getEmail(),
                requestDto.getName(),
                requestDto.getPassword());

        return UserDto.from(user);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto requestDto) throws UserNotFoundException {
        Token token = userService.login(requestDto.getEmail(),requestDto.getPassword());
        return token;
    }

    @PostMapping("/logout")
    public Token logout(@RequestBody LogoutRequestDto requestDto){
        userService.logout(requestDto.getToken());
        return null;
    }

    @GetMapping("validate/{token}")
    public UserDto validateToken(@PathVariable String token){
        User user = userService.validateToken(token);
        return UserDto.from(user);
    }

    @GetMapping("users/{id}")
    public UserDto getUserById(@PathVariable String id){
        return null;
    }
}
