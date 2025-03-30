package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepo extends CrudRepository<DeviceEntity, Long> {
}
