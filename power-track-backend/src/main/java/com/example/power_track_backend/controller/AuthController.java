package com.example.power_track_backend.controller;

import com.example.power_track_backend.dto.request.UserLoginDto;
import com.example.power_track_backend.dto.request.UserRegisterDto;
import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.dto.response.UserDto;
import com.example.power_track_backend.service.UserService;
import com.example.power_track_backend.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<UserDto>> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        // ToDo используется старый метод регистрации из userService. Нужно будет создать класс AuthService и вынести в него реализацию для местных register и login
        UserDto userDto = userService.registerUser(userRegisterDto);

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                userDto
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Map<String, String>>> loginUser(@Valid @RequestBody UserLoginDto request) throws AuthenticationException {
        Map<String, String> response = jwtService.authenticateAndGenerateToken(request); //Todo вот этот новый, но он в jwtService, его нужно вынести оттуда.

        return ResponseEntity.ok(CommonResponse.success(
                HttpStatus.OK.value(),
                response
        ));
    }
}
