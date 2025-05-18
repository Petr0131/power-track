package com.example.power_track_backend.service.recommendation;

import com.example.power_track_backend.RecommendationPriority;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.RecommendationEntity;
import com.example.power_track_backend.entity.ReportEntity;
import org.springframework.stereotype.Component;

@Component
public class RecommendationFactory {
    public RecommendationEntity createBaseRecommendation(ReportEntity report, DeviceEntity device, String formattedMessage, Double potentialSavings, RecommendationPriority priority) {

        return RecommendationBuilder.builder()
                .message(formattedMessage)
                .house(report.getHouseEntity())
                .device(device)
                .report(report)
                .potentialSavings(potentialSavings)
                .priority(priority)
                .build();
    }

    // Рекомендация для дома в целом
    public RecommendationEntity createRecommendationForHouse(ReportEntity report, String customMessage, Double potentialSavings, RecommendationPriority priority) {
        return RecommendationBuilder.builder()
                .message(customMessage)
                .house(report.getHouseEntity())
                .report(report)
                .potentialSavings(potentialSavings)
                .build();
    }
}