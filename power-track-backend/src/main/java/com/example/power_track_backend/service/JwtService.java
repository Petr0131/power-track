package com.example.power_track_backend.service;

import com.example.power_track_backend.config.JwtConfig;
import com.example.power_track_backend.config.MyUserDetails;
import com.example.power_track_backend.dto.request.UserLoginDto;
import com.example.power_track_backend.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService; //Todo Как например тут я его внедрил вместе UserService

    @Autowired
    public JwtService(@Lazy AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtConfig = jwtConfig;
        // Генерация ключа из секретного значения
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Map<String, String> authenticateAndGenerateToken(UserLoginDto request) throws AuthenticationException {
        // Аутентификация пользователя
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Загрузка данных пользователя
        MyUserDetails myUserDetails = myUserDetailsService.loadUserByUsername(request.getUsername());

        // Генерация JWT-токена
        String token = generateToken(myUserDetails);

        // Создание Map для возврата результата
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", myUserDetails.getId().toString()); // ID пользователя
        response.put("username", myUserDetails.getUsername()); // Имя пользователя

        return response;
    }

    // Генерация токена
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(jwtConfig.getExpiration())))
                .signWith(secretKey)
                .compact();
    }

    // Проверка токена
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> Long.valueOf(claims.get("sub", String.class)));
    }

    // Извлечение имени пользователя из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Проверка срока действия токена
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Универсальный метод для извлечения данных из токена
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }
}