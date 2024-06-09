package com.example.userserviceproject.services;

import com.example.userserviceproject.models.Token;
import com.example.userserviceproject.models.User;
import com.example.userserviceproject.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                UserRepository userRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }
    public User signup(String email,
                       String name,
                       String password){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmailVerified(true);
        //save user object to database
        return userRepository.save(user);
    }
    public Token login(String email, String password){
        return null;
    }
    public void logout(String token){
        return;
    }

    public User validateToken(String token){
        return null;

    }
}
