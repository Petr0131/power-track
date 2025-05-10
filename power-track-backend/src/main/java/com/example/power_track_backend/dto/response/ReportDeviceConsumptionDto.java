package com.example.power_track_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "deviceId", "deviceName"})
public class ReportDeviceConsumptionDto {
    private Long id;
    private Double totalConsumption;
    private Double dayConsumption;
    private Double nightConsumption;
    private Double estimatedCost; // Затраты этого прибора
    private Long deviceId;
    private String deviceName;

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
    
    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
