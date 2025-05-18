package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.EnergyEfficiencyCategory;
import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class LowEfficiencySpecification implements Specification<DeviceEntity>{
    @Override
    public boolean isSatisfiedBy(DeviceEntity device) {
        return device.getEnergyEfficiency().getCoefficient() >= EnergyEfficiencyCategory.C.getCoefficient();
    }
}
