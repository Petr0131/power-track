package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.request.UserRegisterDto;
import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.UserDto;
import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.UserAlreadyExistException;
import com.example.power_track_backend.exception.UserNotFoundException;
import com.example.power_track_backend.mapper.UserMapper;
import com.example.power_track_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
// @CrossOrigin(origins = "http://localhost:3000")  // Указываем фронтенд
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<UserDto>> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
            UserDto userDto = userService.registerUser(userRegisterDto);

            return ResponseEntity.ok(CommonResponse.success(
                    HttpStatus.OK.value(),
                    userDto // Возвращаем Dto
            ));
    }
    @GetMapping("/{username}")
    public ResponseEntity<CommonResponse<UserDto>> getUserByUsername(@PathVariable String username){
            UserDto userDto = userService.getUserByUsername(username); // Получаем DTO из сервиса

            return ResponseEntity.ok(CommonResponse.success(
                    HttpStatus.OK.value(),
                    userDto // Возвращаем Dto
            ));
    }

    @DeleteMapping("/{username}")
    @Transactional
    public ResponseEntity<CommonResponse<String>> deleteUserByUsername(
            @PathVariable String username,
            @RequestParam String password
    ){
        String deletedUsername = userService.deleteUserByUsername(username, password);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                deletedUsername
        ));
    }
}
