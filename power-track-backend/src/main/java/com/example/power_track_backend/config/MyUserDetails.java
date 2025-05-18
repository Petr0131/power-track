package com.example.power_track_backend.config;

import com.example.power_track_backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {
    private final UserEntity userEntity;

    @Autowired
    public MyUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Если роль null или пустая, возвращаем роль "USER"
        if (userEntity.getRole() == null || userEntity.getRole().trim().isEmpty()) { //ToDo сделать перечисление для ролей. Чтобы нельзя было вписать что угодно.
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            //ToDo пользователь не должен указывать себе роль.
            //Todo изначально нужно создать 1 пользователя админа, а после чего сделать эндпоинт для выдачи прав администратора,к которому имеет доступ только сам админ.
            //ToDo использовать patch, а не put запрос.

            //ToDo Правильная организации Security в проекте https://vc.ru/dev/1929855-avtorizatsiya-i-avtentifikatsiya-v-java-s-jwt
        }

        return Arrays.stream(userEntity.getRole().split(", "))
                .map(role -> "ROLE_" + role.trim()) // Добавляем префикс "ROLE_"
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    public Long getId() { return userEntity.getId(); }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Можно настроить логику истечения срока действия аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Можно настроить блокировку аккаунта
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Можно настроить истечение срока действия пароля
    }

    @Override
    public boolean isEnabled() {
        return true; // Можно настроить активацию/деактивацию аккаунта
    }
}
