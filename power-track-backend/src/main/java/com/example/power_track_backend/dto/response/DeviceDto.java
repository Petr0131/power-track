package com.example.power_track_backend.dto.response;

import com.example.power_track_backend.DeviceProfile;

public class DeviceDto {
    private DeviceProfile deviceType; // Тип устройства из enum
    private String name;
    private Double devicePower;
    private Integer averageDailyUsageMinutes; // Время работы в день // ToDo добавить валидацию.

    // private String deviceEnergyEfficiency; // ToDo решить стоит ли оставить данное поле.

    public DeviceProfile getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceProfile deviceType) {
        this.deviceType = deviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDevicePower() {
        return devicePower;
    }

    public void setDevicePower(Double devicePower) {
        this.devicePower = devicePower;
    }

    public Integer getAverageDailyUsageMinutes() {
        return averageDailyUsageMinutes;
    }

    public void setAverageDailyUsageMinutes(Integer averageDailyUsageMinutes) {
        this.averageDailyUsageMinutes = averageDailyUsageMinutes;
    }
}
