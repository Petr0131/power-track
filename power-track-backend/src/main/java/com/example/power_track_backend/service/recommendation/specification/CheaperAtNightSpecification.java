package com.example.power_track_backend.service.recommendation.specification;

import com.example.power_track_backend.entity.DeviceEntity;
import com.example.power_track_backend.entity.HouseEntity;
import org.springframework.stereotype.Component;

@Component
public class CheaperAtNightSpecification implements Specification<DeviceEntity>{
    @Override
    public boolean isSatisfiedBy(DeviceEntity device){
        HouseEntity house = device.getHouseEntity(); // ToDo явная передача или так?
        return house.getNightTariff() < house.getDayTariff();
    }
}
