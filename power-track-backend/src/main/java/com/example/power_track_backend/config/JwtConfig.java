package com.example.power_track_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // Время жизни токена в миллисекундах

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }
}
