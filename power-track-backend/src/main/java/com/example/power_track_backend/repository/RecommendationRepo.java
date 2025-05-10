package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.RecommendationEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RecommendationRepo extends CrudRepository<RecommendationEntity, Long> {
    @Modifying
    @Query("DELETE FROM RecommendationEntity r WHERE r.deviceEntity.id = :deviceId")
    void deleteByDeviceId(@Param("deviceId") Long deviceId);
}
