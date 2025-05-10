package com.example.power_track_backend.service.recommendation;

import com.example.power_track_backend.dto.response.RecommendationDto;
import com.example.power_track_backend.entity.ReportEntity;
import com.example.power_track_backend.exception.ReportNotFoundException;
import com.example.power_track_backend.mapper.RecommendationMapper;
import com.example.power_track_backend.repository.ReportRepo;
import com.example.power_track_backend.service.recommendation.strategy.Strategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final ReportRepo reportRepo;
    private final RecommendationMapper recommendationMapper;
    private final List<Strategy> strategies;

    public RecommendationService(ReportRepo reportRepo, RecommendationMapper recommendationMapper ,List<Strategy> strategies) {
        this.reportRepo = reportRepo;
        this.recommendationMapper = recommendationMapper;
        this.strategies = strategies;
    }

    @Transactional
    public List<RecommendationDto> generateRecommendationsForReport(Long reportId) {
        // Находим отчет по ID
        ReportEntity report = reportRepo.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found"));

        // Генерируем рекомендации для найденного отчета
        List<RecommendationDto> allRecommendations = new ArrayList<>();
        for (Strategy strategy : strategies) {
            allRecommendations.addAll(strategy.generateRecommendations(report));
        }

        return allRecommendations;
    }
}
