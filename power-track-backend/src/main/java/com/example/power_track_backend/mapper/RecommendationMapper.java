package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.RecommendationDto;
import com.example.power_track_backend.entity.RecommendationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    @Mapping(source = "deviceEntity.id", target = "deviceId")
    @Mapping(source = "deviceEntity.name", target = "deviceName")
    @Mapping(source = "houseEntity.id", target = "houseId")
    @Mapping(source = "reportEntity.id", target = "reportId")
    RecommendationDto toDto(RecommendationEntity entity);
}
