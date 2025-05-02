package com.example.power_track_backend.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String message) { super(message); }
}
