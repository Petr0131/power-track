package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.HouseEntity;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepo extends CrudRepository<HouseEntity, Long> {
}
