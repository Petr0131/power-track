package com.example.power_track_backend.entity;

import com.example.power_track_backend.DeviceProfile;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devices")
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceProfile deviceType; // Тип устройства из enum
    private String name;
    private Double devicePower;
    private Integer averageDailyUsageMinutes; // Время работы в день // ToDo добавить валидацию.

    // private String deviceEnergyEfficiency; // ToDo решить стоит ли оставить данное поле.
    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;
    @OneToMany(mappedBy = "deviceEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportDeviceConsumptionEntity> consumptionEntities = new ArrayList<>();

}
