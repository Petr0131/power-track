package com.example.power_track_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;
    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
    @NotNull(message = "Total consumption cannot be null")
    @Min(value = 0, message = "Total consumption must be at least 0")
    private Double totalConsumption;
    @NotNull(message = "Total cost cannot be null")
    @Min(value = 0, message = "Total cost must be at least 0")
    private Double totalCost;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;
    @OneToMany(mappedBy = "reportEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendationEntity> recommendations = new ArrayList<>();

    @OneToMany(mappedBy = "reportEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportDeviceConsumptionEntity> deviceConsumptions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public HouseEntity getHouseEntity() {
        return houseEntity;
    }

    public void setHouseEntity(HouseEntity houseEntity) {
        this.houseEntity = houseEntity;
    }

    public List<RecommendationEntity> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationEntity> recommendations) {
        this.recommendations = recommendations;
    }

    public List<ReportDeviceConsumptionEntity> getDeviceConsumptions() {
        return deviceConsumptions;
    }

    public void setDeviceConsumptions(List<ReportDeviceConsumptionEntity> deviceConsumptions) {
        this.deviceConsumptions = deviceConsumptions;
    }
}
