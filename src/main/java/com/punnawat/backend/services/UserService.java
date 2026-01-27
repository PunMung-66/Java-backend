package com.punnawat.backend.services;

import com.punnawat.backend.entity.User;
import com.punnawat.backend.expections.UserException;
import com.punnawat.backend.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(String email, String password, String name) throws UserException {
        // Validate
        if (Objects.isNull(email)){
            throw UserException.createEmailNull();
        }
        if (Objects.isNull(password)){
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(name)){
            throw UserException.createNameNull();
        }
        // Verify
        if (userRepository.existsByEmail(email)){
            throw UserException.createEmailDuplicated();
        }
        //Save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);
        return userRepository.save(entity);
    }

    public String verifyLogin(String email, String password) throws UserException {
        // Validate
        if (Objects.isNull(email)){
            throw UserException.verifyLoginEmailNull();
        }
        if (Objects.isNull(password)){
            throw UserException.verifyLoginPasswordNull();
        }

        // Verify
        Optional<User> existingAccount = userRepository.findByEmail(email);

        if (existingAccount.isEmpty()) {
            throw UserException.verifyLoginFailed();
        }

        String rawPassword = password;
        String encodedPassword = existingAccount.get().getPassword();
        if (!passwordEncoder.matches(rawPassword, encodedPassword)){
            throw UserException.verifyLoginFailed();
        }

        return "LOGIN_SUCCESS";
    }
}
