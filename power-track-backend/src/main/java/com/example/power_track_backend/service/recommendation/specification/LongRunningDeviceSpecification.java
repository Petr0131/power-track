package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class LongRunningDeviceSpecification implements Specification<DeviceEntity> {
    private static final int LONG_RUNNING_THRESHOLD_MINUTES = 6 * 60;
    @Override
    public boolean isSatisfiedBy(DeviceEntity device) {
        return device.getAverageDailyUsageMinutes() > LONG_RUNNING_THRESHOLD_MINUTES;
    }
}
