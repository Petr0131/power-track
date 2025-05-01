package com.example.power_track_backend.config;

import com.example.power_track_backend.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Заготовки некоторых аккаунтов с определенными правами доступа. В виде хардКода.
    // Настройка доступа по ролям. 20мин.
    @Bean
    public UserDetailsService userDetailsService(){

        // UserDetails admin = User.builder().username("admin").password(encoder.encode("admin")).roles("USER", "ADMIN").build();

        // UserDetails user = User.builder().username("user").password(encoder.encode("user")).roles("USER").build();

        return new MyUserDetailsService();
    }

    // Настройка фильтров доступа относительно конкретных точек
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Отключаем CSRF-защиту
                .csrf(AbstractHttpConfigurer::disable)
                // Разрешаем доступ ко всем запросам без проверки
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // Отключаем форму логина, чтобы не происходил редирект на страницу авторизации
                .formLogin(AbstractHttpConfigurer::disable)
                .build();

        /*return http.csrf(AbstractHttpConfigurer::disable) // Отключение csrf защиты
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/welcome", "/user/new-user",
                                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**"
                        ).permitAll() // разрешен доступ всем, можно указывать EndPoints через запятую
                        .requestMatchers("/user/test").authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();*/
    }

    // Компонент механизма аутентификации
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
