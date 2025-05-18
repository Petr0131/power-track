package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.DeviceDto;
import com.example.power_track_backend.entity.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceMapper {
    DeviceDto toDto(DeviceEntity deviceEntity);

    @Mapping(target = "houseEntity", ignore = true)
    @Mapping(target = "consumptionEntities", ignore = true)
    DeviceEntity toEntity(DeviceDto deviceDto);
}
