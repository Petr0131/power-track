package com.example.power_track_backend.exception;

public class HouseAlreadyExistException extends RuntimeException{
    public HouseAlreadyExistException(String message) {
        super(message);
    }
}
