package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.ReportDto;
import com.example.power_track_backend.entity.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    @Mapping(target = "houseId", source = "houseEntity.id")
    ReportDto toDto(ReportEntity reportEntity);
}
