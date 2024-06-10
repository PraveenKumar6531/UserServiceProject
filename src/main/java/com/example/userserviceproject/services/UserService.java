package com.example.userserviceproject.services;

import com.example.userserviceproject.exceptions.UserNotFoundException;
import com.example.userserviceproject.models.Token;
import com.example.userserviceproject.models.User;
import com.example.userserviceproject.repositories.TokenRepository;
import com.example.userserviceproject.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                UserRepository userRepository,
                TokenRepository tokenRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
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
    public Token login(String email, String password) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UserNotFoundException("User with email: " + email + " Not found");
        }
        boolean isMatched = bCryptPasswordEncoder.matches(password,user.getHashedPassword());
        Token token = new Token();
        if(!isMatched) {
            throw new UserNotFoundException("Password for " + user.getName() + " did not match");
        }
        token.setUser(user);
        token.setExpiryAt(new Date());
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        tokenRepository.save(token);
        return token;
    }
    public void logout(String tokenValue){
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(tokenValue,false);
        if(optionalToken.isEmpty()){
            return;
        }
        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
        return;
    }

    public User validateToken(String tokenValue){
        Optional<Token> token = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue,false, new Date());
        if(token.isEmpty()){
            return null;
        }
        return token.get().getUser();

    }
}
