package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.HouseDto;
import com.example.power_track_backend.entity.HouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HouseMapper {

    HouseDto toDto(HouseEntity houseEntity);

}
