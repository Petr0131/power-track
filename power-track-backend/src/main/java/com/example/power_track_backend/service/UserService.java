package com.example.power_track_backend.service;

import com.example.power_track_backend.dto.request.UserRegisterDto;
import com.example.power_track_backend.dto.response.UserDto;
import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.InvalidPasswordException;
import com.example.power_track_backend.exception.UserAlreadyExistException;
import com.example.power_track_backend.exception.UserNotFoundException;
import com.example.power_track_backend.mapper.UserMapper;
import com.example.power_track_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        if (userRepo.existsByUsername(userRegisterDto.getUsername())) {
            throw new UserAlreadyExistException("User with this username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userRegisterDto.getUsername());
        // Модификация passwordEncoder
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        userRepo.save(user);
        return userMapper.toDto(user);
    }

    public UserDto getUserByUsername(String username) throws UserNotFoundException {

        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userMapper.toDto(user);
    }

    public String deleteUserByUsername(String username, String password) throws UserNotFoundException, InvalidPasswordException {
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        // Проверяем пароль
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password for user with username: " + username);
        }

        userRepo.deleteByUsername(username);
        return user.getUsername();
    }

}
