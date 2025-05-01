package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.HouseEntity;
import com.example.power_track_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HouseRepo extends CrudRepository<HouseEntity, Long> {

    // Проверка существования такого имени дома у текущего пользователя
    boolean existsByNameAndUserEntityId(String name, Long userId);

    boolean existsByName(String name);

    Optional<HouseEntity> findByName(String name);

    Optional<HouseEntity> findByNameAndUserEntityId(String name, Long userId);

    List<HouseEntity> findAllByUserEntityId(Long userId);

    @Modifying
    void deleteByName(String name);
}
