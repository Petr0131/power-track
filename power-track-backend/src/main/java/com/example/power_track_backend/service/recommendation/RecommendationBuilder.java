package com.example.power_track_backend.service.recommendation;

import com.example.power_track_backend.RecommendationPriority;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.entity.RecommendationEntity;
import com.example.power_track_backend.entity.ReportEntity;

public class RecommendationBuilder {
    private RecommendationEntity recommendation;

    private RecommendationBuilder() {
        this.recommendation = new RecommendationEntity();
    }

    public static RecommendationBuilder builder() {
        return new RecommendationBuilder();
    }

    public RecommendationBuilder message(String message) {
        recommendation.setMessage(message);
        return this;
    }

    public RecommendationBuilder house(HouseEntity house) {
        recommendation.setHouseEntity(house);
        return this;
    }

    public RecommendationBuilder device(DeviceEntity device) {
        recommendation.setDeviceEntity(device);
        return this;
    }

    public RecommendationBuilder report(ReportEntity report) {
        recommendation.setReportEntity(report);
        return this;
    }

    public RecommendationBuilder potentialSavings(Double savings) {
        recommendation.setPotentialSavings(savings);
        return this;
    }

    public RecommendationBuilder priority(RecommendationPriority priority) {
        recommendation.setPriority(priority);
        return this;
    }

    public RecommendationEntity build() {
        return recommendation;
    }
}
