package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.ReportDeviceConsumptionDto;
import com.example.power_track_backend.entity.ReportDeviceConsumptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportDeviceConsumptionMapper {
    @Mapping(target = "deviceId", source = "deviceEntity.id")
    @Mapping(target = "deviceName", source = "deviceEntity.name")
    ReportDeviceConsumptionDto toDto(ReportDeviceConsumptionEntity entity);

    @Mapping(target = "reportEntity", ignore = true)
    @Mapping(target = "deviceEntity.id", source = "deviceId")
    @Mapping(target = "deviceEntity.name", source = "deviceName")
    ReportDeviceConsumptionEntity toEntity(ReportDeviceConsumptionDto dto);
}
