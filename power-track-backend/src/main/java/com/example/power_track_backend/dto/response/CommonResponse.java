package com.example.power_track_backend.dto.response;



import java.time.LocalDateTime;

public class CommonResponse<T> {
    private final T data;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    private CommonResponse(T data, int status, String message, LocalDateTime timestamp) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Фабричный метод для успешного ответа
    public static <T> CommonResponse<T> success(int status, T data) {
        return new CommonResponse<>(data, status, "success", LocalDateTime.now());
    }

    // Фабричный метод для ошибочного ответа
    public static <T> CommonResponse<T> error(int status, String message) {
        return new CommonResponse<>(null, status, message, LocalDateTime.now());
    }
    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}