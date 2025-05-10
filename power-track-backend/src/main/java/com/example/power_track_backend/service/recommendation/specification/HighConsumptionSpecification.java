package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.entity.DeviceEntity;
import org.springframework.stereotype.Component;

@Component
public class HighConsumptionSpecification implements Specification<DeviceEntity> {

    // ToDo переработать данный класс. Сделать логику по определению высокого СУТОЧНОГО потребления устройством.
    @Override
    public boolean isSatisfiedBy(DeviceEntity device) {
        return device.getPower() > 800;
    }
}
