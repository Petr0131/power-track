package com.example.power_track_backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "houses",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "name"})
                // Строка выше гарантирует уникальность комбинации айди пользователя и имени дома,
                // Это позволяет иметь уникальные имена домов у одного пользователя, при этом у других они могут встречаться.
})
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer rooms;
    private Integer residents;
    private Double dayTariff;
    private Double nightTariff;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceEntity> deviceEntities = new ArrayList<>();
    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reportEntities = new ArrayList<>();
    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendationEntity> recommendationEntities = new ArrayList<>();

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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<DeviceEntity> getDeviceEntities() {
        return deviceEntities;
    }

    public void setDeviceEntities(List<DeviceEntity> deviceEntities) {
        this.deviceEntities = deviceEntities;
    }

    public List<ReportEntity> getReportEntities() {
        return reportEntities;
    }

    public void setReportEntities(List<ReportEntity> reportEntities) {
        this.reportEntities = reportEntities;
    }

    public List<RecommendationEntity> getRecommendationEntities() {
        return recommendationEntities;
    }

    public void setRecommendationEntities(List<RecommendationEntity> recommendationEntities) {
        this.recommendationEntities = recommendationEntities;
    }
}
