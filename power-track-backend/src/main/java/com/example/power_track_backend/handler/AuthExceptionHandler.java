package com.example.power_track_backend.handler;

import com.example.power_track_backend.dto.response.CommonResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE) // Повышаем приоритет относительно GlobalExceptionHandler
@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CommonResponse<?>> handleHouseAlreadyExist(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CommonResponse.error(HttpStatus.CONFLICT.value(), "Authentication failed. The username or password is incorrect."));
    }
}
