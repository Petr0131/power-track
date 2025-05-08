package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.EnergyEfficiencyCategory;
import com.example.power_track_backend.entity.DeviceEntity;

public class LowEfficiencySpecification implements Specification<DeviceEntity>{
    @Override
    public boolean isSatisfiedBy(DeviceEntity device) {
        // ToDo протестировать работоспособность.
        return device.getEnergyEfficiency().getCoefficient() >= EnergyEfficiencyCategory.C.getCoefficient();
    }
}
