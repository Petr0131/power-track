package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class AlwaysOnDeviceSpecification implements Specification<DeviceEntity> {
    private static final int ALWAYS_ON_THRESHOLD_MINUTES = 20 * 60;
    @Override
    public boolean isSatisfiedBy(DeviceEntity device) {
        return device.getAverageDailyUsageMinutes() >= ALWAYS_ON_THRESHOLD_MINUTES;
    }
}
