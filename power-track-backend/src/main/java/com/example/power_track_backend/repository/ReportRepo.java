package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.ReportEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepo extends CrudRepository<ReportEntity, Long> {
    Optional<List<ReportEntity>> findByHouseEntityIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
            Long houseId, LocalDate startDate, LocalDate endDate);

    List<ReportEntity> findAllByHouseEntityId(Long houseId);
}
