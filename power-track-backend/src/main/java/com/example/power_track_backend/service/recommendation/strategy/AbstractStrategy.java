package com.example.power_track_backend.service.recommendation.strategy;

import com.example.power_track_backend.dto.response.RecommendationDto;
import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.RecommendationEntity;
import com.example.power_track_backend.entity.ReportEntity;
import com.example.power_track_backend.mapper.RecommendationMapper;
import com.example.power_track_backend.repository.RecommendationRepo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStrategy implements Strategy {

    protected final RecommendationMapper recommendationMapper;
    protected final RecommendationRepo recommendationRepo;

    public AbstractStrategy(RecommendationMapper recommendationMapper, RecommendationRepo recommendationRepo) {
        this.recommendationMapper = recommendationMapper;
        this.recommendationRepo = recommendationRepo;
    }

    @Override
    public List<RecommendationDto> generateRecommendations(ReportEntity report) {
        List<RecommendationDto> recommendations = new ArrayList<>();

        for (DeviceEntity device : report.getHouseEntity().getDeviceEntities()) {
            if (isSatisfiedBy(device)) {
                RecommendationEntity recommendationEntity = createRecommendation(report, device);

                RecommendationEntity savedRecommendation = recommendationRepo.save(recommendationEntity);

                recommendations.add(recommendationMapper.toDto(savedRecommendation));
            }
        }

        return recommendations;
    }

    protected abstract boolean isSatisfiedBy(DeviceEntity device);

    protected abstract RecommendationEntity createRecommendation(ReportEntity report, DeviceEntity device);
}
