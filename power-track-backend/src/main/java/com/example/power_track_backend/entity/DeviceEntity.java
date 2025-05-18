package com.example.power_track_backend.entity;

import com.example.power_track_backend.DeviceProfile;
import com.example.power_track_backend.EnergyEfficiencyCategory;
import com.example.power_track_backend.UsageTimePeriod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devices",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"house_id", "name"})
                // Строка выше гарантирует уникальность комбинации айди дома и имени устройства,
                // Это позволяет иметь уникальные имена устройств у одного дома, при этом у других они могут встречаться.
        })
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "DeviceProfile cannot be null")
    @Column(nullable = false)
    private DeviceProfile deviceProfile;
    @NotBlank(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;
    @Min(value = 1, message = "Power must be at least 1 watt")
    @NotNull(message = "Power cannot be null")
    @Column(nullable = false)
    private Integer power;
    @Min(value = 1, message = "Count must be at least 1")
    @NotNull(message = "Count cannot be null")
    @Column(nullable = false)
    private Integer count;
    @Min(value = 1, message = "Average daily usage must be at least 1 minute")
    @Max(value = 1440, message = "Average daily usage cannot exceed 1440 minutes (24 hours)")
    @NotNull(message = "AverageDailyUsageMinutes cannot be null")
    @Column(nullable = false)
    private Integer averageDailyUsageMinutes; // Время работы в день
    @Enumerated(EnumType.STRING)
    @NotNull(message = "EnergyEfficiencyCategory cannot be null")
    @Column(nullable = false)
    private EnergyEfficiencyCategory energyEfficiency = EnergyEfficiencyCategory.B;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "UsageTimePeriod cannot be null")
    @Column(nullable = false)
    private UsageTimePeriod usageTimePeriod;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;
    @OneToMany(mappedBy = "deviceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportDeviceConsumptionEntity> consumptionEntities = new ArrayList<>();

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

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPower() { return power; }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getCount() { return count; }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAverageDailyUsageMinutes() { return averageDailyUsageMinutes; }

    public void setAverageDailyUsageMinutes(Integer averageDailyUsageMinutes) {
        this.averageDailyUsageMinutes = averageDailyUsageMinutes;
    }

    public EnergyEfficiencyCategory getEnergyEfficiency() {
        return energyEfficiency;
    }

    public void setEnergyEfficiency(EnergyEfficiencyCategory energyEfficiency) { this.energyEfficiency = energyEfficiency; }

    public UsageTimePeriod getUsageTimePeriod() {
        return usageTimePeriod;
    }

    public void setUsageTimePeriod(UsageTimePeriod usageTimePeriod) {
        this.usageTimePeriod = usageTimePeriod;
    }

    public HouseEntity getHouseEntity() { return houseEntity; }

    public void setHouseEntity(HouseEntity houseEntity) {
        this.houseEntity = houseEntity;
    }

    public List<ReportDeviceConsumptionEntity> getConsumptionEntities() {
        return consumptionEntities;
    }

    public void setConsumptionEntities(List<ReportDeviceConsumptionEntity> consumptionEntities) {
        this.consumptionEntities = consumptionEntities;
    }
}
