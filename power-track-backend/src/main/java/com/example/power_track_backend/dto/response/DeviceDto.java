package com.example.power_track_backend.dto.response;

import com.example.power_track_backend.DeviceProfile;
import com.example.power_track_backend.EnergyEfficiencyCategory;

public class DeviceDto {

    private Long id;
    private DeviceProfile deviceProfile; // Тип устройства из enum
    private String name;
    private Integer power;
    private Integer Count;
    private Integer averageDailyUsageMinutes; // Время работы в день // ToDo добавить валидацию.
    private EnergyEfficiencyCategory energyEfficiency; // ToDo решить стоит ли оставить данное поле.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceProfile getDeviceProfile() {
        return deviceProfile;
    }

    public void setDeviceProfile(DeviceProfile deviceProfile) {
        this.deviceProfile = deviceProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public Integer getAverageDailyUsageMinutes() {
        return averageDailyUsageMinutes;
    }

    public void setAverageDailyUsageMinutes(Integer averageDailyUsageMinutes) {
        this.averageDailyUsageMinutes = averageDailyUsageMinutes;
    }

    public EnergyEfficiencyCategory getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(EnergyEfficiencyCategory energyEfficiency) {
        this.energyEfficiency = energyEfficiency;
    }
}
