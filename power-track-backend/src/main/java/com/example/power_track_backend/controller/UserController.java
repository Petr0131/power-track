package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.UserAlreadyExistException;
import com.example.power_track_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
// @CrossOrigin(origins = "http://localhost:3000")  // Указываем фронтенд
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to unprotected page";
    }

    @GetMapping("/test")
    public String test(){
        return "Welcome to test page";
    }

    @PostMapping("/new-user")
    public ResponseEntity<CommonResponse<Long>> registerUser(@RequestBody UserEntity userEntity) {
            userService.registerUser(userEntity);
            return ResponseEntity.ok(CommonResponse.success(
                    HttpStatus.OK.value(),
                    userEntity.getId()
            ));
    }
}
