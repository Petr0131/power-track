package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.UserDto;
import com.example.power_track_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<CommonResponse<UserDto>> getUserByUsername(@PathVariable String username){
            UserDto userDto = userService.getUserByUsername(username); // Получаем DTO из сервиса

            return ResponseEntity.ok(CommonResponse.success(
                    HttpStatus.OK.value(),
                    userDto
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

    @PatchMapping("/{username}")
    @Transactional
    public ResponseEntity<CommonResponse<UserDto>> updateUserPartially(
            @PathVariable String username,
            @RequestBody Map<String, Object> updates) {

        UserDto updatedUserDto = userService.updateUserPartially(username, updates);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                updatedUserDto
        ));
    }
}
