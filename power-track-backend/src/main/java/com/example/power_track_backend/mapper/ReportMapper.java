package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.ReportDeviceConsumptionDto;
import com.example.power_track_backend.dto.response.ReportDto;
import com.example.power_track_backend.entity.ReportDeviceConsumptionEntity;
import com.example.power_track_backend.entity.ReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    @Mapping(target = "houseId", source = "houseEntity.id")
    ReportDto toDto(ReportEntity reportEntity);

    // Todo Вынести в отдельный маппер?
    // ToDo Поиграться с параметрами которые нужно либо не нужно маппить

    @Mapping(target = "deviceId", source = "deviceEntity.id")
    @Mapping(target = "deviceName", source = "deviceEntity.name")
    ReportDeviceConsumptionDto toDto(ReportDeviceConsumptionEntity entity);

    @Mapping(target = "reportEntity", ignore = true)
    @Mapping(target = "deviceEntity.id", source = "deviceId")
    @Mapping(target = "deviceEntity.name", source = "deviceName")
    ReportDeviceConsumptionEntity toEntity(ReportDeviceConsumptionDto dto);
}
