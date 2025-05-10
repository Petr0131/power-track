package com.example.power_track_backend.dto.response;

import com.example.power_track_backend.RecommendationPriority;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "deviceId", "deviceName"})
public class RecommendationDto {
    private Long id;
    private String message;
    private Double potentialSavings;
    private RecommendationPriority priority;
    private Long reportId;
    private Long houseId;
    private Long deviceId;
    private String deviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getPotentialSavings() {
        return potentialSavings;
    }

    public void setPotentialSavings(Double potentialSavings) {
        this.potentialSavings = potentialSavings;
    }

    public RecommendationPriority getPriority() {
        return priority;
    }

    public void setPriority(RecommendationPriority priority) {
        this.priority = priority;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
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
