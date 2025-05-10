package com.example.power_track_backend.handler;

import com.example.power_track_backend.dto.response.CommonResponse;
import com.example.power_track_backend.exception.ReportAlreadyExistException;
import com.example.power_track_backend.exception.ReportNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE) // Повышаем приоритет относительно GlobalExceptionHandler
@ControllerAdvice
public class ReportExceptionHandler {
    @ExceptionHandler(ReportAlreadyExistException.class)
    public ResponseEntity<CommonResponse<?>> handleReportAlreadyExist(ReportAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CommonResponse.error(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> handleReportNotFound(ReportNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CommonResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
}
