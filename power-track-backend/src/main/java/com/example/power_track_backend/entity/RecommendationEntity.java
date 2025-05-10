package com.example.power_track_backend.entity;

import com.example.power_track_backend.RecommendationPriority;
import jakarta.persistence.*;

@Entity
@Table(name = "recommendations")
public class RecommendationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Double potentialSavings;
    private RecommendationPriority priority;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;
    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private ReportEntity reportEntity;
    @ManyToOne
    @JoinColumn(name = "device_id") // Может быть null, например если рекомендация для дома в целом.
    private DeviceEntity deviceEntity;

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

    public HouseEntity getHouseEntity() {
        return houseEntity;
    }

    public void setHouseEntity(HouseEntity houseEntity) {
        this.houseEntity = houseEntity;
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
