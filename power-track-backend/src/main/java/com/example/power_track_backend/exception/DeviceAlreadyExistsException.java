package com.example.power_track_backend.exception;

public class DeviceAlreadyExistsException extends RuntimeException {
    public DeviceAlreadyExistsException(String message) { super(message); }
}
