package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.UserDto;
import com.example.power_track_backend.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "houseDtoList", ignore = true)
    UserDto toDto(UserEntity userEntity);
}
