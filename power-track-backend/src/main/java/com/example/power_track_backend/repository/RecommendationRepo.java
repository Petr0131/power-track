package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.RecommendationEntity;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepo extends CrudRepository<RecommendationEntity, Long> {
}
