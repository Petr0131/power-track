package com.example.power_track_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.util.List;

@JsonPropertyOrder({"id", "houseId"})
public class ReportDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalConsumption;
    private Double totalCost;
    private Long houseId;
    private List<ReportDeviceConsumptionDto> deviceConsumptions;

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

    public List<ReportDeviceConsumptionDto> getDeviceConsumptions() {
        return deviceConsumptions;
    }

    public void setDeviceConsumptions(List<ReportDeviceConsumptionDto> deviceConsumptions) {
        this.deviceConsumptions = deviceConsumptions;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }
}
