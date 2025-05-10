package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.AdviceEntity;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdviceRepo extends CrudRepository<AdviceEntity, Long> {
    @Override
    @NonNull
    List<AdviceEntity> findAll();
}
