package com.example.power_track_backend.controller;

import com.example.power_track_backend.entity.UserEntity;
import com.example.power_track_backend.exception.UserAlreadyExistException;
import com.example.power_track_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity regUser(@RequestBody UserEntity userEntity){
        try {
            userService.registerUser(userEntity);
            return ResponseEntity.ok("User with id - " + userEntity.getId() + ". registration successful");
        }
        catch (UserAlreadyExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("ERROR - " + e.getMessage());
        }
    }
}
