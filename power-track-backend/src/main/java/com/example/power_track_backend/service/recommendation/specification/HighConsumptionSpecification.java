package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.CalculationConstants;
import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class HighConsumptionSpecification implements Specification<DeviceEntity> {
    private static final double HIGH_DAY_CONSUMPTION = 5;

    @Override
    public boolean isSatisfiedBy(DeviceEntity device) {
        return device.getPower() / CalculationConstants.WATT_TO_KILOWATT * device.getAverageDailyUsageMinutes() /
                CalculationConstants.MINUTES_IN_HOUR * device.getCount() > HIGH_DAY_CONSUMPTION;
    }
}
