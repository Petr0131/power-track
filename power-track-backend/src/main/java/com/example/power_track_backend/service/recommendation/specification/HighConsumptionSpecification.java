package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.stereotype.Service;

@Service
public class HighConsumptionSpecification implements Specification<DeviceEntity> {
    @Override
    public boolean isSatisfiedBy(DeviceEntity device) {
        return device.getPower() > 800;
    }
}
