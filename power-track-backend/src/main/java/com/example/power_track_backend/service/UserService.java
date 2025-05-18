package com.example.power_track_backend.service;

import com.example.power_track_backend.CurrencyType;
import com.example.power_track_backend.dto.request.UserRegisterDto;
import com.example.power_track_backend.dto.response.UserDto;
import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.InvalidPasswordException;
import com.example.power_track_backend.exception.UserAlreadyExistException;
import com.example.power_track_backend.exception.UserNotFoundException;
import com.example.power_track_backend.mapper.UserMapper;
import com.example.power_track_backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

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

        //ToDo код ниже следует вынести в маппер.
        UserEntity user = new UserEntity();
        user.setUsername(userRegisterDto.getUsername());

        // Устанавливаем роль по умолчанию, если она не указана
        user.setRole(userRegisterDto.getRole() != null && !userRegisterDto.getRole().trim().isEmpty()
                ? userRegisterDto.getRole()
                : "USER");

        // Устанавливаем валюту по умолчанию, если она не указана
        user.setCurrencyType(userRegisterDto.getCurrencyType() != null
                ? userRegisterDto.getCurrencyType()
                : CurrencyType.RUB);

        // Модификация passwordEncoder
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        userRepo.save(user);
        return userMapper.toDto(user);
    }

    public UserDto getUserByUsername(String username) {

        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userMapper.toDto(user);
    }

    public String deleteUserByUsername(String username, String password) {
        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        // Проверяем пароль
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password for user with username: " + username);
        }

        userRepo.deleteByUsername(username);
        return user.getUsername();
    }

    public UserDto updateUserPartially(String username, Map<String, Object> updates) {
        // Находим пользователя по имени
        UserEntity userEntity = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Создаем мапу обработчиков для каждого поля
        Map<String, Consumer<Object>> fieldUpdaters = Map.of(
                "password", value -> userEntity.setPassword(passwordEncoder.encode(value.toString())),
                "role", value -> userEntity.setRole(value.toString()),
                "currencyType", value -> {
                    try {
                        userEntity.setCurrencyType(CurrencyType.valueOf(value.toString().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid currency type: " + value);
                    }
                }
        );

        // Обновляем поля пользователя на основе входных данных
        updates.forEach((key, value) -> {
            Consumer<Object> updater = fieldUpdaters.get(key);
            if (updater != null) {
                updater.accept(value);
            } else {
                throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Сохраняем обновленного пользователя в базе данных
        UserEntity updatedUserEntity = userRepo.save(userEntity);

        // Преобразуем сущность в DTO и возвращаем
        return userMapper.toDto(updatedUserEntity);
    }
}
