package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepo extends CrudRepository<DeviceEntity, Long> {
    boolean existsByNameAndHouseEntityId(String name, Long houseId);
    boolean existsByName(String name);
    Optional<DeviceEntity> findByName(String name);
    Optional<DeviceEntity> findByNameAndHouseEntityId(String name, Long houseId);
    List<DeviceEntity> findAllByHouseEntityId(Long houseId);
}
