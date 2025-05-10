package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.AdviceDto;
import com.example.power_track_backend.entity.AdviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdviceMapper {
    AdviceDto toDto(AdviceEntity adviceEntity);
    AdviceEntity toEntity(AdviceDto adviceDto);
}
