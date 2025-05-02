package com.example.power_track_backend.dto.response;

public class HouseDto {
    private Long id;
    private String name;
    private Integer rooms;
    private Integer residents;
    private Double dayTariff;
    private Double nightTariff;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getResidents() {
        return residents;
    }

    public void setResidents(Integer residents) {
        this.residents = residents;
    }

    public Double getDayTariff() {
        return dayTariff;
    }

    public void setDayTariff(Double dayTariff) {
        this.dayTariff = dayTariff;
    }

    public Double getNightTariff() {
        return nightTariff;
    }

    public void setNightTariff(Double nightTariff) {
        this.nightTariff = nightTariff;
    }
}
