package com.example.power_track_backend.exception;

public class HouseNotFoundException extends RuntimeException{
    public HouseNotFoundException(String message) {
        super(message);
    }
}
