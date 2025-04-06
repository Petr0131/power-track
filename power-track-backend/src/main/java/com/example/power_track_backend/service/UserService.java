package com.example.power_track_backend.service;

import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.UserAlreadyExistException;
import com.example.power_track_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserEntity userEntity)  {
        if(userRepo.findByUsername(userEntity.getUsername()).isPresent()){
            throw new UserAlreadyExistException("User with this username already exists");
        }
        // Модификация passwordEncoder
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepo.save(userEntity);
    }

}
