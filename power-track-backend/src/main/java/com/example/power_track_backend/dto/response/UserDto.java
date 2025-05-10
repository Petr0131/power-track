package com.example.power_track_backend.dto.response;

import com.example.power_track_backend.CurrencyType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Исключает null поля в ответе который приходит клиенту, когда он получает DTO. // Todo убрать
public class UserDto {

    private Long id;
    private String username;
    private String role;
    private CurrencyType currencyType;
    private List<HouseDto> houseDtoList = new ArrayList<>();

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public List<HouseDto> getHouseDtoList() {
        return houseDtoList;
    }

    public void setHouseDtoList(List<HouseDto> houseDtoList) {
        this.houseDtoList = houseDtoList;
    }
}
