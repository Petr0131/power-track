package com.example.power_track_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "report_device_consumptions")
public class ReportDeviceConsumptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Total consumption cannot be null")
    @Min(value = 0, message = "Total consumption must be at least 0")
    private Double totalConsumption;
    @NotNull(message = "Day consumption cannot be null")
    @Min(value = 0, message = "Day consumption must be at least 0")
    private Double dayConsumption;
    @NotNull(message = "Night consumption cannot be null")
    @Min(value = 0, message = "Night consumption must be at least 0")
    private Double nightConsumption;
    @NotNull(message = "Estimated cost cannot be null")
    @Min(value = 0, message = "Estimated cost must be at least 0")
    private Double estimatedCost; // Затраты этого прибора

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private  ReportEntity reportEntity;
    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity deviceEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public Double getDayConsumption() {
        return dayConsumption;
    }

    public void setDayConsumption(Double dayConsumption) {
        this.dayConsumption = dayConsumption;
    }

    public Double getNightConsumption() {
        return nightConsumption;
    }

    public void setNightConsumption(Double nightConsumption) {
        this.nightConsumption = nightConsumption;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public ReportEntity getReportEntity() {
        return reportEntity;
    }

    public void setReportEntity(ReportEntity reportEntity) {
        this.reportEntity = reportEntity;
    }

    public DeviceEntity getDeviceEntity() {
        return deviceEntity;
    }

    public void setDeviceEntity(DeviceEntity deviceEntity) {
        this.deviceEntity = deviceEntity;
    }
}
