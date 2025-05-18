package com.example.power_track_backend.entity;

import com.example.power_track_backend.CurrencyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username cannot be blank or empty")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Password cannot be blank or empty")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;
    @NotBlank(message = "Role cannot be blank or empty")
    @Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN)$", message = "Role must be either 'USER' or 'ADMIN'")
    private String role;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Currency type cannot be null")
    private CurrencyType currencyType;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HouseEntity> houseEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public List<HouseEntity> getHouseEntities() {
        return houseEntities;
    }

    public void setHouseEntities(List<HouseEntity> houseEntities) {
        this.houseEntities = houseEntities;
    }
}
