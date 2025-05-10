package com.example.power_track_backend.service.recommendation.strategy;

import com.example.power_track_backend.dto.response.RecommendationDto;
import com.example.power_track_backend.entity.ReportEntity;

import java.util.List;

public interface Strategy {
    List<RecommendationDto> generateRecommendations(ReportEntity report);
}
