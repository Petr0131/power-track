package com.example.power_track_backend.mapper;

import com.example.power_track_backend.dto.response.UserDto;
import com.example.power_track_backend.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING) //  указание componentModel сделает маппер Spring-бином, и его можно будет внедрять через @Autowired.
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "houseDtoList", ignore = true)
    UserDto toDto(UserEntity userEntity);

    @Mapping(target = "houseDtoList", ignore = true)
    UserDto toUserDtoSummary(UserEntity userEntity);

    // UserDto toUserDtoDetails(UserEntity userEntity);

    // UserEntity toEntity(UserDto userDto); // Todo Вряд ли мне это пригодиться.
}
