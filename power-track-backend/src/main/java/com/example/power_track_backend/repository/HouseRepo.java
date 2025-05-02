package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.HouseEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HouseRepo extends CrudRepository<HouseEntity, Long> {

    // Проверка существования такого имени дома у текущего пользователя
    boolean existsByNameAndUserEntityId(String name, Long userId);

    boolean existsByName(String name);

    Optional<HouseEntity> findByName(String name);

    Optional<HouseEntity> findByNameAndUserEntityId(String name, Long userId);

    List<HouseEntity> findAllByUserEntityId(Long userId);
}
