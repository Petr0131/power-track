package com.example.power_track_backend.handler;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.exception.HouseAlreadyExistException;
import com.example.power_track_backend.exception.HouseNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE) // Повышаем приоритет относительно GlobalExceptionHandler
@ControllerAdvice
public class HouseExceptionHandler {
    @ExceptionHandler(HouseAlreadyExistException.class)
    public ResponseEntity<CommonResponse<?>> handleHouseAlreadyExist(HouseAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CommonResponse.error(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(HouseNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> handleHouseNotFound(HouseNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
}
