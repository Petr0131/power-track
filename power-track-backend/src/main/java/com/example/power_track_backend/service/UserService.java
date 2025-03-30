package com.example.power_track_backend.service;

import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.UserAlreadyExistException;
import com.example.power_track_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity registerUser(UserEntity userEntity) throws UserAlreadyExistException {
        if(userRepo.findByUsername(userEntity.getUsername()).isPresent()){
            throw new UserAlreadyExistException("User with this username already exists");
        }
        return userRepo.save(userEntity);
    }
}
